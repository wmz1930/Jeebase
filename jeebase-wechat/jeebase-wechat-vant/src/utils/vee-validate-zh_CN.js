import { Validator } from 'vee-validate' // 引入校验框架

Validator.extend('phone', {
  messages: {
    zh_CN: field => field + '必须是11位手机号码'
  },
  validate: value => {
    return value.length === 11 && /^((13|14|15|16|17|18|19)[0-9]{1}\d{8})$/.test(value)
  },
  events: 'blur'
})

const formatFileSize = function(size) {
  const units = ['Byte', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
  const threshold = 1024
  size = Number(size) * threshold
  const i = size === 0 ? 0 : Math.floor(Math.log(size) / Math.log(threshold))
  return (((size / Math.pow(threshold, i)).toFixed(2) * 1) + ' ' + (units[i]))
}
const fieldName = '' // 原来的i18n的fieldName会显示绑定的name值，就是英文，实际项目中不需要
const zh_CN = {
  name: 'zh_CN',
  attributes: {},
  messages: {
    _default: () => `${fieldName}无效`,
    after: (field, [target]) => `${fieldName}必须在${target}之后`,
    alpha_dash: () => `${fieldName}能够包含字母数字字符、破折号和下划线`,
    alpha_num: () => `${fieldName}只能包含字母数字字符`,
    alpha_spaces: () => `${fieldName}只能包含字母字符和空格`,
    alpha: () => `${fieldName}只能包含字母字符`,
    before: (field, [target]) => `${fieldName}必须在${target}之前`,
    between: (field, [min, max]) => `${fieldName}必须在${min}与${max}之间`,
    confirmed: (field, [confirmedField]) => `${fieldName}不能和${confirmedField}匹配`,
    credit_card: () => `${fieldName}格式错误`,
    date_between: (field, [min, max]) => `${fieldName}必须在${min}和${max}之间`,
    date_format: (field, [format]) => `${fieldName}必须符合${format}格式`,
    decimal: (field, [decimals = '*'] = []) => `${fieldName}必须是数字，且能够保留${decimals === '*' ? '' : decimals}位小数`,
    digits: (field, [length]) => `${fieldName}必须是数字，且精确到${length}位数`,
    dimensions: (field, [width, height]) => `${fieldName}必须在${width}像素与${height}像素之间`,
    email: () => `${fieldName}不是一个有效的邮箱`,
    ext: () => `${fieldName}不是一个有效的文件`,
    image: () => `${fieldName}不是一张有效的图片`,
    included: () => `${fieldName}不是一个有效值`,
    integer: () => `${fieldName}必须是整数`,
    ip: () => `${fieldName}不是一个有效的地址`,
    length: (field, [length, max]) => {
      if (max) {
        return `${fieldName}长度必须在${length}到${max}之间`
      }
      return `${fieldName}长度必须为${length}`
    },
    max: (field, [length]) => `${fieldName}不能超过${length}个字符`,
    max_value: (field, [max]) => `${fieldName}必须小于或等于${max}`,
    mimes: () => `${fieldName}不是一个有效的文件类型`,
    min: (field, [length]) => `${fieldName}必须至少有${length}个字符`,
    min_value: (field, [min]) => `${fieldName}必须大于或等于${min}`,
    excluded: () => `${fieldName}不是一个有效值`,
    numeric: () => `${fieldName}只能包含数字字符`,
    regex: () => `${fieldName}格式无效`,
    required: () => `${fieldName}不能为空`,
    size: (field, [size]) => `${fieldName}必须小于${formatFileSize(size)}`,
    url: () => `${fieldName}不是一个有效的url`,
    phone: () => `${fieldName}必须是11位手机号码`
  }
}

export default zh_CN
