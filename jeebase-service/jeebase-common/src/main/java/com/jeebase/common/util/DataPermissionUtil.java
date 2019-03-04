package com.jeebase.common.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;

import java.util.ArrayList;
import java.util.List;

public final class DataPermissionUtil {
    public static String convertDataPermission(String originalSql, String orgIdAlias, List<String> orgIdList, boolean ownQuery, String userIdAlias, String userId) throws Throwable
    {
        Select select = (Select) CCJSqlParserUtil.parse(originalSql);
        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect)selectBody;
        Expression where = plainSelect.getWhere();
        //拼接in
        InExpression inExpression = new InExpression();
        inExpression.setLeftExpression(new StringValue("''"));
        List<Expression> expressions = new ArrayList<Expression>();
        StringBuffer inSql = new StringBuffer(orgIdAlias);
        inSql.append(" in ( ");
        //如果操作用户的数据权限不为空
        if(!CollectionUtils.isEmpty(orgIdList))
        {
            for (String orgId : orgIdList)
            {
                inSql.append("'").append(orgId).append("',");
            }
            inSql.deleteCharAt(inSql.length()-1).append(") ");
        }
        else
        {
            inSql.append("'NODATA'").append(" ) ");
        }
        //如果操作用户没有本部门的权限，但是可以查询自己的数据权限
        if (ownQuery && !StringUtils.isEmpty(userIdAlias) && !StringUtils.isEmpty(userId))
        {
            inSql.append("or (").append(userIdAlias).append(" = '").append(userId).append("') ");
        }
        Expression enhancedCondition =  CCJSqlParserUtil.parseCondExpression(inSql.toString());
        if (where != null) {
            Expression expressionWhere = new Parenthesis(where);
            AndExpression andExpression = new AndExpression(expressionWhere, new Parenthesis(enhancedCondition));
            plainSelect.setWhere(andExpression);
        } else {
            plainSelect.setWhere(enhancedCondition);
        }

        select.setSelectBody(plainSelect);

        return select.toString();
    }
}
