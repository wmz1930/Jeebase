<template>
  <a-card :bordered="false" class="content">
    <div class="table-page-search-wrapper">
      <a-form-model layout="inline">
        <a-row :gutter="48">
          <a-col :md="6" :sm="24">
            <a-form-model-item label="资源名称">
              <a-input
                v-model.trim="treeQuery.resourceName"
                placeholder="请输入资源名称"
                :max-length="32"
                @keyup.enter.native="searchTreeData" />
            </a-form-model-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-model-item label="资源标识">
              <a-input
                v-model.trim="treeQuery.resourceKey"
                placeholder="请输入资源标识"
                :max-length="32"
                @keyup.enter.native="searchTreeData" />
            </a-form-model-item>
          </a-col>
          <a-col :md="!advanced && 6 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
              <a-button type="primary" @click="searchTreeData">查询</a-button>
              <a-button style="margin-left: 8px" @click="resetQuery">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form-model>
    </div>

    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="handleCreate(false)">新建</a-button>
      <!-- <a-button type="primary" icon="cloud-download" @click="handleDownload" style="margin-left: 8px">导出</a-button> -->
    </div>

    <a-table :columns="columns" :rowKey="row=>row.id" :data-source="list" >
      <span slot="resourceType" slot-scope="text, record">
        <span>{{ record.resourceType | typeNameFilter }}</span>
      </span>
      <span slot="action" slot-scope="text, record">
        <a @click="handleCreate(record)">新增</a>
        <a-divider type="vertical" />
        <a @click="handleUpdate(record)">编辑</a>
        <a-divider type="vertical" />
        <a @click="handleDelete(record)">删除</a>
      </span>
    </a-table>

    <a-modal
      :title="textMap[dialogStatus]"
      v-model="dialogFormVisible"
      :width="800"
      :maskClosable="false"
      :destroyOnClose="true"
      @cancel="() => dialogFormVisible = false">
      <a-form-model
        ref="resourceForm"
        :model="resourceForm"
        :rules="rules"
        :label-col="resourceLabelCol"
        :wrapper-col="resourceWrapperCol">
        <a-form-model-item label="资源名称" prop="resourceName">
          <a-input v-model.trim="resourceForm.resourceName" placeholder="权限或者菜单的名称" :maxLength="32" />
        </a-form-model-item>
        <a-form-model-item label="资源标识" prop="resourceKey">
          <a-input v-model.trim="resourceForm.resourceKey" placeholder="系统用来判断权限的唯一key" :maxLength="32" />
        </a-form-model-item>
        <a-form-model-item label="资源类型" prop="resourceType">
          <a-radio-group v-model="resourceForm.resourceType" name="resourceType">
            <a-radio :value="1">模块</a-radio>
            <a-radio :value="2">菜单</a-radio>
            <a-radio :value="4">接口</a-radio>
            <a-radio :value="3">按钮</a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="资源图标" prop="resourceIcon">
          <a-input v-model.trim="resourceForm.resourceIcon" placeholder="菜单的图标，不是菜单可以不填" :maxLength="32" />
        </a-form-model-item>
        <a-form-model-item label="资源path" prop="resourcePath">
          <a-tooltip class="item" effect="dark" content="1、单页面路由地址 2、外链地址以http://或https://开头" placement="right">
            <a-input v-model.trim="resourceForm.resourcePath" placeholder="浏览器地址栏显示的url" />
          </a-tooltip>
        </a-form-model-item>
        <a-form-model-item label="资源链接" prop="resourceUrl">
          <a-tooltip class="item" effect="dark" content="1、一级菜单填Layout 2、包含子菜单的二级菜单填nested 3、最后子菜单填写页面对应路径 4、接口填写请求路径" placement="right">
            <a-input v-model.trim="resourceForm.resourceUrl" placeholder="菜单对应前台页面的路径" />
          </a-tooltip>
        </a-form-model-item>
        <a-form-model-item label="资源排序" prop="resourceLevel">
          <a-input v-model.number="resourceForm.resourceLevel" placeholder="菜单排序" :maxLength="32" />
        </a-form-model-item>
        <a-form-model-item v-show="resourceForm.resourceType === 2" label="页面名称" prop="resourcePageName">
          <a-tooltip class="item" effect="dark" content="如果前端页面开启了tagView，一定要跟页面中定义的name值保持名称一致，否则不能keep-alive" placement="right">
            <a-input v-model.trim="resourceForm.resourcePageName" placeholder="前端页面定义的名称" :maxLength="32" />
          </a-tooltip>
        </a-form-model-item>
        <a-form-model-item label="是否缓存" prop="resourceCache">
          <a-radio-group v-model="resourceForm.resourceCache" name="resourceCache">
            <a-radio :value="true">是</a-radio>
            <a-radio :value="false">否</a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="是否展示" prop="resourceShow" name="resourceShow">
          <a-radio-group v-model="resourceForm.resourceShow">
            <a-radio :value="true">是</a-radio>
            <a-radio :value="false">否</a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="备注信息">
          <a-input v-model.trim="resourceForm.description" :autoSize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="请输入备注信息" />
        </a-form-model-item>
      </a-form-model>
      <div slot="footer" class="dialog-footer">
        <a-button @click="dialogFormVisible = false">取消</a-button>
        <a-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</a-button>
        <a-button v-else type="primary" @click="updateData">确定</a-button>
      </div>
    </a-modal>
  </a-card>
</template>

<script>
/**
  Auth: Jeebase
  Created: 2018/1/19-14:54
*/
import { fetchResourceList, createResource, deleteResource, updateResource, checkResourceKey } from '@/api/system/resource'

export default {
  name: 'ResourceTable',
  components: { },
  filters: {
    typeNameFilter (type) {
      const typeNameMap = {
        1: '模块',
        2: '菜单',
        4: '接口',
        3: '按钮'
      }
      return typeNameMap[type]
    }
  },
  data () {
    var validResourceKey = (rule, value, callback) => {
      var keyData = {
        id: this.resourceForm.id,
        resourceKey: value
      }
      checkResourceKey(keyData).then(response => {
        if (!response.data) {
          callback(new Error('资源标识已存在'))
        } else {
          callback()
        }
      })
    }
    return {
      advanced: false,
      expandAll: false,
      resourceLabelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      resourceWrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      selectedRowKeys: [],
      selectedRows: [],
      list: [],
      baseList: [],
      rootFlag: false,
      expandTitle: 'resourceTable.resourceName',
      expandName: 'resourceName',
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '添加'
      },
      dialogPvVisible: false,
      treeQuery: {
        parentId: 0,
        resourceKey: '',
        resourceName: ''
      },
      resourceForm: {
        id: '',
        parentId: 0,
        resourceName: '',
        resourceKey: '',
        resourceType: 1,
        resourceIcon: '',
        resourcePath: '',
        resourceUrl: '',
        resourceLevel: '',
        resourceCache: true,
        resourceShow: true,
        resourcePageName: '',
        children: [], // 必须加，否则新增的节点不显示
        description: ''
      },
      // 表头
      columns: [
        {
          title: '资源名称',
          align: 'left',
          dataIndex: 'resourceName'
        },
        {
          title: '资源标识',
          align: 'center',
          dataIndex: 'resourceKey'
        },
        {
          title: '资源类型',
          align: 'center',
          dataIndex: 'resourceType',
          scopedSlots: { customRender: 'resourceType' }
        },
        {
          title: '资源链接',
          align: 'center',
          dataIndex: 'resourceUrl'
        },
        {
          title: '资源图标',
          align: 'center',
          dataIndex: 'resourceIcon'
        },
        {
          title: '资源排序',
          align: 'center',
          dataIndex: 'resourceLevel'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '150px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      rules: {
        resourceName: [
          { required: true, message: '请输入资源名称', trigger: 'blur' },
          { min: 2, max: 32, message: '长度在 2 到 32 个字符', trigger: 'blur' }
        ],
        resourceKey: [
          { required: true, message: '请输入资源标识', trigger: 'blur' },
          { min: 2, max: 32, message: '长度在 2 到 32 个字符', trigger: 'blur' },
          { validator: validResourceKey, trigger: 'blur' }
        ],
        resourceType: [
          { required: true, message: '请选择资源类型', trigger: 'blur' }
        ],
        resourceIcon: [
          { required: true, message: '请输入资源图标', trigger: 'blur' },
          { min: 2, max: 32, message: '长度在 2 到 32 个字符', trigger: 'blur' }
        ],
        resourcePath: [
          { required: true, message: '请输入资源path', trigger: 'blur' },
          { min: 2, max: 255, message: '长度在 2 到 255 个字符', trigger: 'blur' }
        ],
        resourceUrl: [
          { required: true, message: '请输入资源接口', trigger: 'blur' },
          { min: 2, max: 255, message: '长度在 2 到 255 个字符', trigger: 'blur' }
        ],
        resourceLevel: [
          { required: true, message: '请输入资源排序', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请填写备注信息', trigger: 'blur' }
        ]
      },
      args: [null, null, null]
    }
  },
  created () {
    this.getList()
  },
  methods: {
    resetQuery () {
      this.treeQuery = {
        parentId: 0,
        resourceKey: '',
        resourceName: ''
      }
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    getList () {
      this.listLoading = true
      fetchResourceList(this.treeQuery).then(response => {
        var resourceListStr = JSON.stringify(response.data)
        var dataResourceList = JSON.parse(resourceListStr.replace(/"children":\[\]/g, '"children":null'))
        this.list = dataResourceList
        this.baseList = JSON.parse(JSON.stringify(dataResourceList)) // 数组深复制
        this.listLoading = false
      })
    },
    resetResourceForm () {
      this.resourceForm = {
        id: '',
        parentId: 0,
        resourceName: '',
        resourceKey: '',
        resourceType: 1,
        resourceIcon: '',
        resourcePath: '',
        resourceUrl: '',
        resourceLevel: '',
        resourceCache: true,
        resourceShow: true,
        resourcePageName: '',
        children: [], // 必须加，否则新增的节点不显示
        description: ''
      }
    },
    handleCreate (row) {
      this.resetResourceForm()
      if (row) {
        this.rootFlag = false
        this.resourceForm.parentId = row.id
      } else {
        this.rootFlag = true
      }
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        if (this.$refs['resourceForm']) {
          this.$refs['resourceForm'].clearValidate()
        }
      })
    },
    createData () {
      this.$refs['resourceForm'].validate(valid => {
        if (valid) {
          createResource(this.resourceForm).then(response => {
            this.dialogFormVisible = false
            this.resourceForm.id = response.data.id
            if (this.rootFlag) {
              this.resourceForm['children'] = null
              this.list.push(this.resourceForm)
              this.baseList.push(JSON.parse(JSON.stringify(this.resourceForm)))
            } else {
              this.createDataCallBack(this.list)
              this.createDataCallBack(this.baseList)
            }
            this.$message.success('创建成功')
          })
        }
      })
    },
    createDataCallBack (dataList) {
      for (const v of dataList) {
        if (v.id === this.resourceForm.parentId) {
          if (!v.children) {
            v['children'] = null
          }
          this.resourceForm['children'] = null
          v.children.push(JSON.parse(JSON.stringify(this.resourceForm)))
          break
        }
        if (v.children && v.children.length > 0) {
          this.createDataCallBack(v.children)
        }
      }
    },
    handleUpdate (row) {
      this.resourceForm = Object.assign({}, row) // copy obj
      // JSON不接受循环对象——引用它们自己的对象
      delete this.resourceForm.parent
      delete this.resourceForm.children
      this.resourceForm.resourceType = parseInt(this.resourceForm.resourceType)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        if (this.$refs['resourceForm']) {
          this.$refs['resourceForm'].clearValidate()
        }
      })
    },
    updateData () {
      this.$refs['resourceForm'].validate(valid => {
        if (valid) {
          updateResource(this.resourceForm).then(() => {
            this.dialogFormVisible = false
            this.$message.success('更新成功')
            this.updateDataCallBack(this.list)
            this.updateDataCallBack(this.baseList)
          })
        }
      })
    },
    updateDataCallBack (dataList) {
      for (const v of dataList) {
        if (v.id === this.resourceForm.id) {
          Object.assign(v, JSON.parse(JSON.stringify(this.resourceForm)))
          break
        }
        if (v.children && v.children.length > 0) {
          this.updateDataCallBack(v.children)
        }
      }
    },
    handleDelete (row) {
      var that = this
      this.$confirm({
        title: '此操作将永久删除该资源：' + row.resourceName + ', 是否继续?',
        content: '',
        onOk () {
          that.listLoading = true
          deleteResource(row.id).then(() => {
            that.listLoading = false
            that.$message.success('删除成功!')
            that.deleteDataCallBack(row.id, that.list)
            that.deleteDataCallBack(row.id, that.baseList)
          })
        },
        onCancel () {
          that.$message.info('已取消删除')
        }
      })
    },
    deleteDataCallBack (id, dataList) {
      for (const v of dataList) {
        if (v.id === id) {
          const index = dataList.indexOf(v)
          dataList.splice(index, 1)
          break
        }
        if (v.children && v.children.length > 0) {
          this.deleteDataCallBack(id, v.children)
        }
      }
    },
    searchTreeData () {
      this.list = JSON.parse(JSON.stringify(this.baseList))
      if (
        !(
          this.treeQuery.resourceName === '' &&
          this.treeQuery.resourceKey === ''
        )
      ) {
        this.queryData(this.list)
      }
      this.expandAll = true
    },
    queryData (dataList) {
      var haveFlag = false
      var len = dataList.length - 1
      if (len < 0) {
        return haveFlag
      }
      var haveFlagArray = new Array(dataList.length)
      for (let i = len; i >= 0; i--) {
        var isname = this.treeQuery.resourceName !== ''
        var hasname =
          dataList[i].resourceName.indexOf(this.treeQuery.resourceName) >= 0
        var iskey = this.treeQuery.resourceKey !== ''
        var haskey =
          dataList[i].resourceKey.indexOf(this.treeQuery.resourceKey) >= 0

        if (isname && !iskey) {
          if (hasname) {
            haveFlagArray[i] = true
          } else if (
            !(dataList[i].children && dataList[i].children.length > 0)
          ) {
            var index1 = dataList.indexOf(dataList[i])
            dataList.splice(index1, 1)
            continue
          } else {
            haveFlagArray[i] = false
          }
        } else if (!isname && iskey) {
          if (haskey) {
            haveFlagArray[i] = true
          } else if (
            !(dataList[i].children && dataList[i].children.length > 0)
          ) {
            var index2 = dataList.indexOf(dataList[i])
            dataList.splice(index2, 1)
            continue
          } else {
            haveFlagArray[i] = false
          }
        } else if (isname && iskey) {
          if (hasname && haskey) {
            haveFlagArray[i] = true
          } else if (
            !(dataList[i].children && dataList[i].children.length > 0)
          ) {
            var index3 = dataList.indexOf(dataList[i])
            dataList.splice(index3, 1)
            continue
          } else {
            haveFlagArray[i] = false
          }
        }

        if (
          dataList[i] &&
          dataList[i].children &&
          dataList[i].children.length > 0
        ) {
          var childHaveFlag = this.queryData(dataList[i].children)
          if (!childHaveFlag && !haveFlagArray[i]) {
            var index4 = dataList.indexOf(dataList[i])
            dataList.splice(index4, 1)
          }
          if (childHaveFlag) {
            haveFlagArray[i] = true
          }
        }
      }

      if (haveFlagArray.indexOf(true) >= 0) {
        haveFlag = true
      }
      return haveFlag
    }
  }
}
</script>
