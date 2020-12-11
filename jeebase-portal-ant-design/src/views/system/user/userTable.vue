<template>
  <a-card :bordered="false" class="content">
    <div class="table-page-search-wrapper">
      <a-form-model layout="inline">
        <a-row :gutter="48">
          <a-col :md="6" :sm="24">
            <a-form-model-item label="组织机构">
              <a-cascader
                v-model="selectedOrgOptionsQuery"
                :options="orgList"
                :field-names="propsOrg"
                :show-search="{ filter }"
                :display-render="displayRender"
                expand-trigger="hover"
                placeholder="组织机构"
              />
            </a-form-model-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-model-item label="姓名">
              <a-input
                v-model.trim="listQuery.userName"
                placeholder="姓名"
                :max-length="32"
                @keyup.enter.native="handleFilter" />
            </a-form-model-item>
          </a-col>
          <template v-if="advanced">
            <a-col :md="6" :sm="24">
              <a-form-model-item label="手机号码">
                <a-input
                  v-model.trim="listQuery.userMobile"
                  placeholder="手机号码"
                  :max-length="11"
                  @keyup.enter.native="handleFilter" />
              </a-form-model-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-model-item label="邮箱地址">
                <a-input
                  v-model.trim="listQuery.userEmail"
                  placeholder="邮箱地址"
                  :max-length="100"
                  @keyup.enter.native="handleFilter" />
              </a-form-model-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-model-item label="用户角色">
                <a-select v-model="listQuery.roleId" placeholder="用户角色" allow-clear show-search :filter-option="filterOption">
                  <a-select-option v-for="item in roleList" :key="item.roleKey" :value="item.id">
                    {{ item.roleName }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-model-item label="状态">
                <a-select v-model="listQuery.userStatus" placeholder="状态" allow-clear show-search :filter-option="filterOption">
                  <a-select-option v-for="item in statusOption" :key="item.key" :value="item.key">
                    {{ item.label }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-model-item label="开始时间">
                <a-date-picker v-model.trim="listQuery.startDate" placeholder="开始时间" valueFormat="YYYY-MM-DD" style="width:100%;"/>
              </a-form-model-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-model-item label="结束时间">
                <a-date-picker v-model.trim="listQuery.endDate" placeholder="结束时间" valueFormat="YYYY-MM-DD" style="width:100%;"/>
              </a-form-model-item>
            </a-col>
          </template>
          <a-col :md="!advanced && 6 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
              <a-button type="primary" @click="handleFilter">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => listQuery = {}">重置</a-button>
              <a @click="toggleAdvanced" style="margin-left: 8px">
                {{ advanced ? '收起' : '展开' }}
                <a-icon :type="advanced ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form-model>
    </div>

    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="handleCreate">新建</a-button>
      <a-button type="primary" icon="cloud-download" @click="handleDownload" style="margin-left: 8px">导出</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="handleBatchDelete"><a-icon type="delete" />删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px">
          批量操作 <a-icon type="down" />
        </a-button>
      </a-dropdown>
    </div>

    <s-table
      ref="userTable"
      size="default"
      bordered
      :rowKey="row=>row.id"
      :columns="columns"
      :data="loadData"
      showPagination="auto"
      :pagination="userPagination"
      :rowSelection="{ selectedRowKeys: this.selectedRowKeys, onChange: this.onSelectChange }"
    >
      <!-- <div
        slot="expandedRowRender"
        slot-scope="record"
        style="margin: 0">
        <a-row
          :gutter="24"
          :style="{ marginBottom: '12px' }">
          <a-col :span="12" v-for="(role, index) in record.permissions" :key="index" :style="{ marginBottom: '12px' }">
            <a-col :lg="4" :md="24">
              <span>{{ role.permissionName }}：</span>
            </a-col>
            <a-col :lg="20" :md="24" v-if="role.actionEntitySet.length > 0">
              <a-tag color="cyan" v-for="(action, k) in role.actionEntitySet" :key="k">{{ action.describe }}</a-tag>
            </a-col>
            <a-col :span="20" v-else>-</a-col>
          </a-col>
        </a-row>
      </div> -->
      <span slot="userSex" slot-scope="text, record">
        <span>{{ record.userSex | sexNameFilter }}</span>
      </span>
      <span slot="status" slot-scope="text, record">
        <a-tag :color="record.userStatus | statusFilter">{{ record.userStatus | statusNameFilter }}</a-tag>
      </span>
      <span slot="createTime" slot-scope="text, record">
        <span>{{ record.createTime | moment }}</span>
      </span>
      <span slot="action" slot-scope="text, record">
        <a @click="handleUpdate(record)">编辑</a>
        <a-divider type="vertical" />
        <a-dropdown>
          <a class="ant-dropdown-link">
            更多 <a-icon type="down" />
          </a>
          <a-menu slot="overlay">
            <a-menu-item>
              <a href="javascript:;" @click="handleDataPermission(record)">数据权限</a>
            </a-menu-item>
            <a-menu-item>
              <a href="javascript:;" @click="handleResetUserPassword(record)">重置密码</a>
            </a-menu-item>
            <a-menu-item>
              <a href="javascript:;" v-if="record.userStatus!='1'" size="mini" type="success" @click="handleModifyStatus(record,'1')">启用
              </a>
              <a href="javascript:;" v-if="record.userStatus!='0' && record.userStatus!='2'" size="mini" @click="handleModifyStatus(record,'0')">禁用
              </a>
            </a-menu-item>
            <a-menu-item>
              <a href="javascript:;" @click="handleDelete(record)">删除</a>
            </a-menu-item>
          </a-menu>
        </a-dropdown>
      </span>
    </s-table>

    <a-modal :title="textMap[dialogStatus]" :maskClosable="false" :visible="dialogFormVisible" :width="800" @cancel="() => dialogFormVisible = false">
      <a-form-model
        ref="userForm"
        :model="userForm"
        :rules="rules"
        :label-col="userLabelCol"
        :wrapper-col="userWrapperCol">
        <a-form-model-item label="组织机构" prop="orgList">
          <a-cascader
            :options="orgList"
            v-model="selectedOrgOptions"
            :field-names="propsOrg"
            :show-search="{ filter }"
            :display-render="displayRender"
            expand-trigger="hover"
            placeholder="组织机构"
          />
        </a-form-model-item>
        <a-form-model-item label="用户账号" prop="userAccount">
          <a-input v-model="userForm.userAccount" placeholder="输入用户账号" :max-length="32" />
        </a-form-model-item>
        <a-form-model-item label="用户昵称" prop="userNickName">
          <a-input v-model="userForm.userNickName" placeholder="输入用户昵称" :max-length="32" />
        </a-form-model-item>
        <a-form-model-item label="用户姓名" prop="userName">
          <a-input v-model="userForm.userName" placeholder="输入用户姓名" :max-length="32" />
        </a-form-model-item>
        <a-form-model-item label="手机号码" prop="userMobile">
          <a-input v-model="userForm.userMobile" placeholder="输入用户手机号码" :max-length="11" />
        </a-form-model-item>
        <a-form-model-item label="电子邮箱" prop="userEmail">
          <a-input v-model="userForm.userEmail" placeholder="输入用户电子邮箱" :max-length="32" />
        </a-form-model-item>
        <a-form-model-item label="用户角色" prop="roleId">
          <a-select
            v-model="userForm.roleIds"
            placeholder="选择用户角色"
            allow-clear
            show-search
            :filter-option="filterOption"
            mode="multiple">
            <a-select-option v-for="item in roleList" :key="item.roleKey" :value="item.id">
              {{ item.roleName }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="用户地址" prop="areas">
          <a-cascader
            v-model="userForm.areas"
            :options="provinceOptions"
            placeholder="输选择用户地址"
            style="width:100%;" />
        </a-form-model-item>
        <a-form-model-item label="详细地址" prop="street">
          <a-input v-model="userForm.street" placeholder="详细地址" :max-length="120" />
        </a-form-model-item>
        <a-form-model-item label="性别" prop="userSex">
          <a-radio-group v-model="userForm.userSex" name="userSex">
            <a-radio :value="1">男性</a-radio>
            <a-radio :value="0">女性</a-radio>
            <a-radio :value="2">保密</a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="状态" prop="userStatus">
          <a-radio-group v-model="userForm.userStatus" name="userStatus">
            <a-radio :value="1">启用</a-radio>
            <a-radio :value="0">禁用</a-radio>
            <a-radio :value="2">未激活</a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="备注">
          <a-input v-model="userForm.description" :autoSize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="请输入备注信息" />
        </a-form-model-item>
      </a-form-model>
      <div slot="footer" class="dialog-footer">
        <a-button @click="dialogFormVisible = false">取消</a-button>
        <a-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</a-button>
        <a-button v-else type="primary" @click="updateData">修改</a-button>
      </div>
    </a-modal>

    <a-modal v-model="dialogDataPermissionVisible" :maskClosable="false" :destroyOnClose="true" title="设置用户数据权限">
      <a-card class="box-card">
        <div slot="header" class="clearfix">
          <span>组织机构列表</span>
        </div>
        <div class="text item">
          <a-tree
            ref="orgTree"
            checkable
            :tree-data="orgTreeList"
            :replace-fields="treeProps"
            :check-strictly="true"
            :default-expanded-keys="userCheckOrgPermission"
            :default-checked-keys="userCheckOrgPermission"
            @check="computeOrgPermission"
          />
        </div>
      </a-card>
      <div slot="footer" class="dialog-footer">
        <a-button @click="dialogDataPermissionVisible = false">取消</a-button>
        <a-button type="primary" @click="updateDataPermission">确定</a-button>
      </div>
    </a-modal>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { fetchList, createUser, resetUserPassword, deleteUser, batchDeleteUser, updateUser, updateUserStatus, fetchRoleList, updateUserDataPermission, checkUserAccount, checkUserMobile, checkUserEmail, checkUserNickName } from '@/api/system/user'
import { fetchOrgList } from '@/api/system/organization'
import moment from 'moment'
import Data from '@/api/pcaa'
export default {
  name: 'TableList',
  components: {
    STable
  },
  filters: {
    statusFilter (status) {
      const statusMap = {
        1: 'green',
        2: '',
        0: 'pink'
      }
      return statusMap[status]
    },
    statusNameFilter (status) {
      const statusNameMap = {
        1: '启用',
        2: '未激活',
        0: '禁用'
      }
      return statusNameMap[status]
    },
    sexNameFilter (sex) {
      const sexNameMap = {
        1: '男',
        2: '保密',
        0: '女'
      }
      return sexNameMap[sex]
    }
  },
  data () {
    var validUserAccount = (rule, value, callback) => {
      var keyData = {
        id: this.userForm.id,
        userAccount: value
      }
      checkUserAccount(keyData).then(response => {
        if (!response.data) {
          callback(new Error('用户账号已存在'))
        } else {
          callback()
        }
      })
    }
    var validUserNickName = (rule, value, callback) => {
      if (value) {
        var keyData = {
          id: this.userForm.id,
          userNickName: value
        }
        checkUserNickName(keyData).then(response => {
          if (!response.data) {
            callback(new Error('用户昵称已存在'))
          } else {
            callback()
          }
        })
      }
    }
    var validUserMobile = (rule, value, callback) => {
      var keyData = {
        id: this.userForm.id,
        userMobile: value
      }
      checkUserMobile(keyData).then(response => {
        if (!response.data) {
          callback(new Error('手机号已存在'))
        } else {
          callback()
        }
      })
    }
    var validUserEmail = (rule, value, callback) => {
      var keyData = {
        id: this.userForm.id,
        userEmail: value
      }
      checkUserEmail(keyData).then(response => {
        if (!response.data) {
          callback(new Error('电子邮箱已存在'))
        } else {
          callback()
        }
      })
    }
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      userLabelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      userWrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      form: null,
      advanced: false,
      tableKey: 0,
      roleList: null,
      provinceOptions: null,
      treeProps: {
        children: 'children', title: 'organizationName', key: 'id'
      },
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        userName: '',
        userMobile: '',
        userEmail: '',
        roleIds: [],
        organizationId: '',
        userStatus: undefined
      },
      statusOption: [
        { label: '启用', key: '1' },
        { label: '禁用', key: '0' },
        { label: '未激活', key: '2' }
      ],
      dialogFormVisible: false,
      dialogDataPermissionVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑用户信息',
        create: '添加用户'
      },
      userForm: {
        id: '',
        userAccount: '',
        userNickName: '',
        userName: '',
        userMobile: '',
        userEmail: '',
        roleIds: [],
        organizationId: '',
        userSex: 1,
        userStatus: 1,
        areas: [],
        street: '',
        description: ''
      },
      dataPermissionForm: {
        userId: '',
        addDataPermissions: [],
        removeDataPermissions: []
      },
      // 表头
      columns: [
        {
          title: '序号',
          align: 'center',
          dataIndex: 'id'
        },
        {
          title: '组织机构',
          align: 'center',
          dataIndex: 'organizationName'
        },
        {
          title: '账号',
          align: 'center',
          dataIndex: 'userAccount'
        },
        {
          title: '姓名',
          align: 'center',
          dataIndex: 'userName'
        },
        {
          title: '手机号',
          align: 'center',
          dataIndex: 'userMobile'
        },
        {
          title: '邮箱',
          align: 'center',
          dataIndex: 'userEmail'
        },
        {
          title: '角色',
          align: 'center',
          dataIndex: 'roleName'
        },
        {
          title: '性别',
          align: 'center',
          dataIndex: 'userSex',
          scopedSlots: { customRender: 'userSex' }
        },
        {
          title: '注册日期',
          align: 'center',
          dataIndex: 'createTime',
          scopedSlots: { customRender: 'createTime' }
        },
        {
          title: '状态',
          align: 'center',
          dataIndex: 'userStatus',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '150px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      rules: {
        userAccount: [
          { required: true, message: '请输入用户账号', trigger: 'blur' },
          { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' },
          { validator: validUserAccount, trigger: 'blur' }
        ],
        userNickName: [
          { min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur' },
          { validator: validUserNickName, trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '请输入用户姓名', trigger: 'blur' },
          { min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur' }
        ],
        userMobile: [
          {
            pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/,
            required: true,
            message: '请输入正确的手机号',
            trigger: 'blur'
          },
          {
            min: 11,
            max: 11,
            message: '长度在 11 到 11 个字符',
            trigger: 'blur'
          },
          { validator: validUserMobile, trigger: 'blur' }
        ],
        userEmail: [
          {
            type: 'email',
            required: true,
            message: '请输入正确的邮箱',
            trigger: 'blur'
          },
          { min: 5, max: 32, message: '长度在 5 到 32 个字符', trigger: 'blur' },
          { validator: validUserEmail, trigger: 'blur' }
        ],
        roleIds: [
          { required: true, message: '请选择用户角色', trigger: 'change' }
        ],
        organizationId: [
          { required: true, message: '请选择组织机构', trigger: 'change' }
        ],
        userSex: [
          { required: true, message: '请选择用户性别', trigger: 'change' }
        ],
        userStatus: [
          { required: true, message: '请选择用户状态', trigger: 'change' }
        ],
        description: [
          { required: true, message: '请填写备注信息', trigger: 'blur' }
        ]
      },
      downloadLoading: false,
      selectedOrgOptions: [],
      selectedOrgOptionsQuery: [],
      propsOrg: {
        value: 'id',
        label: 'organizationName',
        children: 'children'
      },
      orgList: [],
      orgTreeList: [],
      userCheckOrgPermission: [],
      addOrgPermission: [],
      removeOrgPermission: [],
      selectedRowKeys: [],
      selectedRows: [],
      userPagination: {
        defaultPageSize: 10,
        showQuickJumper: true,
        defaultCurrent: 1,
        showTotal: (total, range) => `共 ${total} 条`
      },
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        if (this.selectedOrgOptionsQuery.length > 0) {
          this.listQuery.organizationId = this.selectedOrgOptionsQuery[this.selectedOrgOptionsQuery.length - 1]
        } else {
          this.listQuery.organizationId = ''
        }
        return fetchList(Object.assign(parameter, this.listQuery))
          .then(res => {
            this.list = res.data
            return res
          })
      }
    }
  },
  created () {
    this.getOrgList()
    this.getRoleList()
    this.getAreaList()
  },
  methods: {
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    displayRender ({ labels }) {
      return labels[labels.length - 1]
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    getList () {
      this.listLoading = true
      if (this.selectedOrgOptionsQuery.length > 0) {
        this.listQuery.organizationId = this.selectedOrgOptionsQuery[this.selectedOrgOptionsQuery.length - 1]
      } else {
        this.listQuery.organizationId = ''
      }
      fetchList(this.listQuery).then(response => {
        this.list = response.data
        this.total = response.count
        this.listLoading = false
      })
    },
    getOrgList () {
      this.listLoading = true
      fetchOrgList({ parentId: 0 }).then(response => {
        this.orgList = response.data
        var orgListStr = JSON.stringify(this.orgList)
        this.orgTreeList = JSON.parse(orgListStr.replace(/"isLeaf":1/g, '"isLeaf":true').replace(/"isLeaf":0/g, '"isLeaf":false'))
        this.listLoading = false
      })
    },
    selectOrgListByLastId (orgList, lastId) {
      // 递归查询机构父机构，用于展示已选中的机构
      var orgStr = ''
      if (orgList) {
        for (var org of orgList) {
          // a-tree的isLeaf必须为boolean类型，这里需要转换一下
          if (org.isLeaf === 1) {
            org.isLeaf = true
          } else {
            org.isLeaf = false
          }
          if (lastId === org.id) {
            return lastId
          } else if (org.children) {
            var childOrg = this.selectOrgListByLastId(org.children, lastId)
            if (childOrg) {
              orgStr = org.id + ',' + childOrg
              return orgStr
            }
          }
        }
      }
      return orgStr
    },
    computeOrgPermission (item, e) {
      var node = e.node
      var checked = e.checked
      if (checked) {
        // 如果原先不存在，则添加到新增列表
        if (this.userCheckOrgPermission.indexOf(node.dataRef.id) === -1) {
          this.addOrgPermission.push(node.dataRef.id)
        }
        // 如果在删除列表中，则从删除列表中删除
        var removeIndex = this.removeOrgPermission.indexOf(node.dataRef.id)
        if (removeIndex > -1) {
          this.removeOrgPermission.splice(removeIndex, 1)
        }
      } else {
        // 如果原先存在，则添加到删除列表
        if (this.userCheckOrgPermission.indexOf(node.dataRef.id) > -1) {
          this.removeOrgPermission.push(node.dataRef.id)
        }
        // 如果在新增列表中，则从新增列表中删除
        var addIndex = this.addOrgPermission.indexOf(node.dataRef.id)
        if (addIndex > -1) {
          this.addOrgPermission.splice(addIndex, 1)
        }
      }
    },
    getRoleList () {
      this.listLoading = true
      fetchRoleList().then(response => {
        this.roleList = response.data
        this.listLoading = false
      })
    },
    getAreaList () {
      var options = []
      for (var key in Data['86']) {
        var citys = []
        for (var keyc in Data[key]) {
          var areas = []
          for (var keya in Data[keyc]) {
            var area = { value: keya, label: Data[keyc][keya] }
            areas.push(area)
          }
          var city = { value: keyc, label: Data[key][keyc], children: areas }
          citys.push(city)
        }
        var province = { value: key, label: Data['86'][key], children: citys }
        options.push(province)
      }
      this.provinceOptions = options
    },
    handleFilter () {
      this.$refs.userTable.refresh(true)
    },
    handleTableRefresh () {
      this.$refs.userTable.refresh()
    },
    resetUserForm () {
      this.userForm = {
        id: '',
        userAccount: '',
        userNickName: '',
        userName: '',
        userMobile: '',
        userEmail: '',
        roleIds: [],
        organizationId: '',
        userSex: 1,
        userStatus: 1,
        area: [],
        street: '',
        description: ''
      }
    },
    resetDataPermissionForm () {
      this.dataPermissionForm = {
        userId: '',
        addDataPermissions: [],
        removeDataPermissions: []
      }
    },
    handleCreate () {
      this.resetUserForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['userForm'].clearValidate()
      })
    },
    createData () {
      if (this.selectedOrgOptions.length > 0) {
        this.userForm.organizationId = this.selectedOrgOptions[this.selectedOrgOptions.length - 1]
      } else {
        this.userForm.organizationId = ''
      }
      this.$refs['userForm'].validate(valid => {
        if (valid) {
          createUser(this.userForm).then(() => {
            this.dialogFormVisible = false
            this.handleFilter()
            this.$message.success('创建成功')
          })
        }
      })
    },
    handleUpdate (row) {
      this.resetUserForm()
      this.userForm = Object.assign({}, row) // copy obj
      if (!this.userForm.areas || this.userForm.areas.length === 0) {
        this.userForm.areas = [
          this.userForm.province,
          this.userForm.city,
          this.userForm.area
        ]
      }

      if (this.userForm.organizationId) {
        var orgStr = this.selectOrgListByLastId(this.orgList, this.userForm.organizationId) + ''
        this.selectedOrgOptions = orgStr.split(',').map(Number)
      }

      if (!(this.userForm.roleIds instanceof Array)) {
        var roleIds = this.userForm.roleIds.split(',')
        var arrRoleIds = []
        for (var roleId of roleIds) {
          arrRoleIds.push(parseInt(roleId))
        }
        this.userForm.roleIds = arrRoleIds
      }

      this.userForm.userStatus = parseInt(this.userForm.userStatus)
      this.userForm.userSex = parseInt(this.userForm.userSex)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['userForm'].clearValidate()
      })
    },
    updateData () {
      if (this.selectedOrgOptions.length > 0) {
        this.userForm.organizationId = this.selectedOrgOptions[this.selectedOrgOptions.length - 1]
      } else {
        this.userForm.organizationId = ''
      }
      this.$refs['userForm'].validate(valid => {
        if (valid) {
          updateUser(this.userForm).then(() => {
            this.handleTableRefresh()
            this.dialogFormVisible = false
            this.$message.success('更新成功')
          })
        }
      })
    },
    handleDataPermission (row) {
      this.dialogStatus = 'update'
      this.dialogDataPermissionVisible = true
      this.resetDataPermissionForm()
      this.userForm = Object.assign({}, row)

      this.addOrgPermission = []
      this.removeOrgPermission = []
      if (this.userForm.dataPermission) {
        this.userCheckOrgPermission = this.userForm.dataPermission.split(',').map(Number)
      } else {
        this.userCheckOrgPermission = []
      }
      this.dataPermissionForm.userId = this.userForm.id
    },
    updateDataPermission () {
      this.dataPermissionForm.addDataPermissions = this.addOrgPermission
      this.dataPermissionForm.removeDataPermissions = this.removeOrgPermission
      updateUserDataPermission(this.dataPermissionForm).then(() => {
        this.handleFilter()
        this.dialogDataPermissionVisible = false
        this.$message.success('更新成功')
      })
    },
    handleResetUserPassword (row) {
      var that = this
      this.$confirm({
        title: '该用户密码将被重置：' + row.userAccount + ', 是否继续?',
        content: '',
        onOk () {
          that.listLoading = true
          resetUserPassword(row.id).then(() => {
            that.listLoading = false
            that.$message.success('重置成功!')
          })
        },
        onCancel () {
          that.$message.info('已取消重置')
        }
      })
    },
    handleDelete (row) {
      var that = this
      this.$confirm({
        title: '此操作将永久删除该用户：' + row.userAccount + ', 是否继续?',
        content: '',
        onOk () {
          that.listLoading = true
          deleteUser(row.id).then(() => {
            that.listLoading = false
            that.$message.success('删除成功!')
            this.handleTableRefresh()
          })
        },
        onCancel () {
          that.$message.info('已取消删除')
        }
      })
    },
    handleBatchDelete (row) {
      var accountList = this.selectedRows.map(function (n) {
        return n.userAccount
      })
      var that = this
      this.$confirm({
        title: '以下用户将被全部删除，是否继续?',
        content: accountList.join(','),
        onOk () {
          that.listLoading = true
          batchDeleteUser(that.selectedRowKeys).then(() => {
            that.listLoading = false
            that.$message.success('删除成功!')
            that.selectedRowKeys = []
            that.selectedRows = []
            that.handleTableRefresh()
          })
        },
        onCancel () {
          that.$message.info('已取消删除')
        }
      })
    },
    handleModifyStatus (row, status) {
      this.listLoading = true
      updateUserStatus(row.id, status).then(() => {
        this.listLoading = false
        row.userStatus = status
        this.$message.success('状态修改成功')
      })
    },
    handleDownload () {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = [
          '序号',
          '账号',
          '姓名',
          '手机号',
          '邮箱',
          '角色',
          '性别',
          '注册时间',
          '状态'
        ]
        const filterVal = [
          'id',
          'userAccount',
          'userName',
          'userMobile',
          'userEmail',
          'roleName',
          'userSex',
          'createTime',
          'userStatus'
        ]
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '用户列表'
        })
        this.downloadLoading = false
      })
    },
    formatJson (filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          if (j === 'createTime') {
            return moment(v[j])
          } else if (j === 'userSex') {
            return this.$options.filters['sexNameFilter'](v[j])
          } else if (j === 'userStatus') {
            return this.$options.filters['statusNameFilter'](v[j])
          } else {
            return v[j]
          }
        })
      )
    },
    filterOption (input, option) {
      return (
        option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      )
    },
    filter (inputValue, path) {
      return path.some(option => option.organizationName.toLowerCase().indexOf(inputValue.toLowerCase()) > -1)
    }
  }
}
</script>
