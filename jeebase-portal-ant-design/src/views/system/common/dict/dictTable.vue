<template>
  <a-card :bordered="false" class="content">
    <div class="table-page-search-wrapper">
      <a-form-model layout="inline">
        <a-row :gutter="48">
          <a-col :md="6" :sm="24">
            <a-form-model-item label="字典名称">
              <a-input
                v-model.trim="treeQuery.dictName"
                placeholder="请输入字典名称"
                :max-length="32"
                @keyup.enter.native="searchTreeData" />
            </a-form-model-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-model-item label="字典值">
              <a-input
                v-model.trim="treeQuery.dictCode"
                placeholder="请输入字典值"
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
          <a-col :md="6" :sm="24">
            <a-alert message="一级列表为字典分类，字典值禁止重复" banner />
          </a-col>
        </a-row>
      </a-form-model>
    </div>
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="handleCreate(false)">新建</a-button>

      <!-- <a-button type="primary" icon="cloud-download" @click="handleDownload" style="margin-left: 8px">导出</a-button> -->
    </div>

    <a-table :columns="columns" :rowKey="row=>row.id" :data-source="list" >
      <span slot="dictStatus" slot-scope="text, record">
        <span>{{ record.dictStatus | statusFilter }}</span>
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
        ref="dictForm"
        :model="dictForm"
        :rules="rules"
        label-width="100px"
        :label-col="dictLabelCol"
        :wrapper-col="dictWrapperCol">
        <a-form-model-item label="字典名称" prop="dictName">
          <a-input v-model.trim="dictForm.dictName" placeholder="输入字典名称" :maxLength="32" />
        </a-form-model-item>
        <a-form-model-item label="字典值" prop="dictCode">
          <a-input v-model.trim="dictForm.dictCode" placeholder="输入字典类型/字典值" :maxLength="32" />
        </a-form-model-item>
        <a-form-model-item label="字典排序" prop="dictOrder">
          <a-input v-model.number="dictForm.dictOrder" placeholder="输入字典排序" :maxLength="32" />
        </a-form-model-item>
        <a-form-model-item label="字典状态" prop="dictStatus">
          <a-radio-group v-model="dictForm.dictStatus">
            <a-radio :value="1">启用</a-radio>
            <a-radio :value="0">禁用</a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="备注信息">
          <a-input v-model.trim="dictForm.description" :autoSize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="请输入备注信息" />
        </a-form-model-item>
      </a-form-model>
      <div slot="footer" class="dialog-footer">
        <a-button @click="dialogFormVisible = false">取消</a-button>
        <a-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</a-button>
        <a-button v-else type="primary" @click="updateData">修改</a-button>
      </div>
    </a-modal>
  </a-card>
</template>

<script>
/**
  Auth: Lei.j1ang
  Created: 2018/1/19-14:54
*/
import { fetchList, createDict, deleteDict, updateDict } from '@/api/system/common/dict'

export default {
  name: 'DictTable',
  components: { },
  filters: {
    statusFilter (status) {
      const statusMap = {
        1: '启用',
        0: '禁用'
      }
      return statusMap[status]
    }
  },
  data () {
    return {
      expandAll: false,
      advanced: false,
      dictLabelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      dictWrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      list: [],
      baseList: [],
      rootFlag: false,
      expandTitle: 'dictTable.dictName',
      expandName: 'dictName',
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '添加'
      },
      dialogPvVisible: false,
      treeQuery: {
        parentId: 0,
        dictName: '',
        dictCode: ''
      },
      dictForm: {
        id: '',
        parentId: 0,
        dictName: '',
        dictCode: '',
        dictStatus: 1,
        dictOrder: '',
        children: [], // 必须加，否则新增的节点不显示
        description: ''
      },
      // 表头
      columns: [
        {
          title: '字典名称',
          align: 'left',
          dataIndex: 'dictName'
        },
        {
          title: '字典值',
          align: 'center',
          dataIndex: 'dictCode'
        },
        {
          title: '字典排序',
          align: 'center',
          dataIndex: 'dictOrder'
        },
        {
          title: '字典状态',
          align: 'center',
          dataIndex: 'dictStatus',
          scopedSlots: { customRender: 'dictStatus' }
        },
        {
          title: '备注信息',
          align: 'center',
          dataIndex: 'description'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '150px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      rules: {
        dictName: [
          { required: true, message: '请输入字典名称', trigger: 'blur' },
          { min: 1, max: 32, message: '长度在 2 到 32 个字符', trigger: 'blur' }
        ],
        dictCode: [
          { required: true, message: '请输入字典值', trigger: 'blur' },
          { min: 1, max: 32, message: '长度在 2 到 32 个字符', trigger: 'blur' }
        ],
        dictOrder: [
          { required: true, message: '请输入字典排序', trigger: 'blur' }
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
        dictName: '',
        dictCode: ''
      }
    },
    getList () {
      this.listLoading = true
      fetchList(this.treeQuery).then(response => {
        var resourceListStr = JSON.stringify(response.data)
        var dataResourceList = JSON.parse(resourceListStr.replace(/"children":\[\]/g, '"children":null'))
        this.list = dataResourceList
        this.baseList = JSON.parse(JSON.stringify(dataResourceList)) // 数组深复制
        this.listLoading = false
      })
    },
    resetDictForm () {
      this.dictForm = {
        id: '',
        parentId: 0,
        dictName: '',
        dictCode: '',
        dictStatus: 1,
        dictOrder: '',
        children: [], // 必须加，否则新增的节点不显示
        description: ''
      }
    },
    handleCreate (row) {
      this.resetDictForm()
      if (row) {
        this.rootFlag = false
        this.dictForm.parentId = row.id
      } else {
        this.rootFlag = true
      }
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        if (this.$refs['dictForm']) {
          this.$refs['dictForm'].clearValidate()
        }
      })
    },
    createData () {
      this.$refs['dictForm'].validate(valid => {
        if (valid) {
          createDict(this.dictForm).then(response => {
            this.dialogFormVisible = false
            this.dictForm.id = response.data.id
            if (this.rootFlag) {
              this.dictForm['children'] = null
              this.list.push(this.dictForm)
              this.baseList.push(
                JSON.parse(JSON.stringify(this.dictForm))
              )
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
        if (v.id === this.dictForm.parentId) {
          if (!v.children) {
            v['children'] = null
          }
          this.dictForm['children'] = null
          v.children.push(JSON.parse(JSON.stringify(this.dictForm)))
          break
        }
        if (v.children && v.children.length > 0) {
          this.createDataCallBack(v.children)
        }
      }
    },
    handleUpdate (row) {
      this.dictForm = Object.assign({}, row) // copy obj
      // JSON不接受循环对象——引用它们自己的对象
      delete this.dictForm.parent
      delete this.dictForm.children
      this.dictForm.dictStatus = parseInt(this.dictForm.dictStatus)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        if (this.$refs['dictForm']) {
          this.$refs['dictForm'].clearValidate()
        }
      })
    },
    updateData () {
      this.$refs['dictForm'].validate(valid => {
        if (valid) {
          updateDict(this.dictForm).then(() => {
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
        if (v.id === this.dictForm.id) {
          Object.assign(v, JSON.parse(JSON.stringify(this.dictForm)))
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
        title: '此操作将永久删除该字典：' + row.dictName + ', 是否继续?',
        content: '',
        onOk () {
          that.listLoading = true
          deleteDict(row.id).then(() => {
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
          this.treeQuery.dictName === '' &&
          this.treeQuery.dictCode === ''
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
        var isname = this.treeQuery.dictName !== ''
        var hasname =
          dataList[i].dictName.indexOf(
            this.treeQuery.dictName
          ) >= 0
        var iskey = this.treeQuery.dictCode !== ''
        var haskey =
          dataList[i].dictCode.indexOf(this.treeQuery.dictCode) >=
          0

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
