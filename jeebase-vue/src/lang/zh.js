export default {
  route: {
    dashboard: '首页',
    introduction: '简述',
    documentation: '文档',
    userInfo: '个人信息',
    system: '系統管理',
    userTable: '用户管理',
    roleTable: '角色管理',
    resourceTable: '资源管理',
    organizationTable: '组织管理'
  },
  navbar: {
    logOut: '退出登录',
    dashboard: '首页',
    github: '项目地址',
    screenfull: '全屏',
    theme: '换肤',
    size: '布局大小'
  },
  login: {
    title: 'Jeebase管理系统登录',
    logIn: '登录',
    userAccount: '账号',
    userPassword: '密码',
    vcode: '验证码',
    any: '随便填',
    thirdparty: '第三方登录'
  },
  documentation: {
    documentation: '文档',
    github: 'Github 地址'
  },
  permission: {
    roles: '你的权限',
    switchRoles: '切换权限'
  },
  guide: {
    description: '引导页对于一些第一次进入项目的人很有用，你可以简单介绍下项目的功能。本 Demo 是基于',
    button: '打开引导'
  },
  table: {
    dynamicTips1: '固定表头, 按照表头顺序排序',
    dynamicTips2: '不固定表头, 按照点击顺序排序',
    dragTips1: '默认顺序',
    dragTips2: '拖拽后顺序',
    title: '标题',
    importance: '重要性',
    type: '类型',
    remark: '点评',
    search: '搜索',
    add: '添加',
    export: '导出',
    reviewer: '审核人',
    id: '序号',
    date: '时间',
    author: '作者',
    readings: '阅读数',
    status: '状态',
    actions: '操作',
    edit: '编辑',
    publish: '发布',
    draft: '草稿',
    delete: '删除',
    startDate: '开始时间',
    endDate: '结束时间',
    cancel: '取 消',
    confirm: '确 定'
  },
  userTable: {
    id: '序号',
    organization: '组织机构',
    userAccount: '账号',
    userNickName: '昵称',
    userName: '姓名',
    userMobile: '手机号',
    userEmail: '邮箱',
    roleName: '角色',
    userSex: '性别',
    userStatus: '状态',
    area: '地区',
    street: '详细地址',
    description: '备注',
    enable: '启用',
    disable: '禁用',
    createTime: '注册时间',
    permissionEdit: '数据权限'
  },
  roleTable: {
    id: '序号',
    parentId: '父级',
    roleName: '角色名称',
    roleKey: '角色标识',
    roleLevel: '角色级别',
    roleStatus: '角色状态',
    createTime: '创建时间',
    resource: '权限',
    enable: '启用',
    disable: '禁用',
    description: '备注'
  },
  resourceTable: {
    id: '序号',
    parentId: '父级',
    resourceName: '资源名称',
    resourceKey: '资源标识',
    resourceType: '资源类型',
    resourceIcon: '资源图标',
    resourcePath: '资源path',
    resourceUrl: '资源链接',
    resourceLevel: '资源排序',
    resourceCache: '是否缓存',
    resourceShow: '是否显示',
    description: '备注'
  },
  organizationTable: {
    id: '序号',
    parentId: '父级',
    organizationName: '组织名称',
    organizationKey: '组织标识',
    organizationType: '组织类型',
    organizationIcon: '组织图标',
    organizationLevel: '组织排序',
    area: '地区',
    street: '详细地址',
    description: '备注'
  },
  dictTable: {
    id: '序号',
    parentId: '字典类型',
    dictName: '字典名称',
    dictCode: '字典值',
    dictOrder: '字典排序',
    dictStatus: '字典状态',
    description: '备注'
  },
  memberTable: {
    id: '序号',
    userAccount: '账号',
    userNickName: '昵称',
    userName: '姓名',
    userMobile: '手机号',
    userEmail: '邮箱',
    roleName: '角色',
    userSex: '性别',
    userStatus: '状态',
    area: '地区',
    description: '备注',
    enable: '启用',
    disable: '禁用',
    createTime: '注册时间'
  },
  errorLog: {
    tips: '请点击右上角bug小图标',
    description: '现在的管理后台基本都是spa的形式了，它增强了用户体验，但同时也会增加页面出问题的可能性，可能一个小小的疏忽就导致整个页面的死锁。好在 Vue 官网提供了一个方法来捕获处理异常，你可以在其中进行错误处理或者异常上报。',
    documentation: '文档介绍'
  },
  excel: {
    export: '导出',
    selectedExport: '导出已选择项',
    placeholder: '请输入文件名(默认excel-list)'
  },
  zip: {
    export: '导出',
    placeholder: '请输入文件名(默认file)'
  },
  theme: {
    change: '换肤',
    documentation: '换肤文档',
    tips: 'Tips: 它区别于 navbar 上的 theme-pick, 是两种不同的换肤方法，各自有不同的应用场景，具体请参考文档。'
  },
  tagsView: {
    refresh: '刷新',
    close: '关闭',
    closeOthers: '关闭其它',
    closeAll: '关闭所有'
  }
}
