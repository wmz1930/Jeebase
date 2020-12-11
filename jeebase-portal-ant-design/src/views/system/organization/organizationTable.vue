<template>
  <a-card :bordered="false" class="content">
    <div class="table-page-search-wrapper">
      <a-form-model layout="inline">
        <a-row :gutter="48">
          <a-col :md="6" :sm="24">
            <a-form-model-item label="组织名称">
              <a-input
                v-model.trim="treeQuery.organizationName"
                placeholder="请输入组织名称"
                :max-length="32"
                @keyup.enter.native="searchTreeData" />
            </a-form-model-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-model-item label="组织标识">
              <a-input
                v-model.trim="treeQuery.organizationKey"
                placeholder="请输入组织标识"
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
      <span slot="organizationType" slot-scope="text, record">
        <span>{{ record.organizationType | typeNameFilter }}</span>
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
        ref="organizationForm"
        :model="organizationForm"
        :rules="rules"
        label-width="100px"
        class="organizationForm"
        :label-col="orgLabelCol"
        :wrapper-col="orgWrapperCol">
        <a-form-model-item label="组织名称" prop="organizationName">
          <a-input v-model.trim="organizationForm.organizationName" placeholder="输入组织名称" :maxLength="32" />
        </a-form-model-item>
        <a-form-model-item label="组织标识" prop="organizationKey">
          <a-input v-model.trim="organizationForm.organizationKey" placeholder="输入组织标识" :maxLength="32" />
        </a-form-model-item>
        <a-form-model-item label="组织类型" prop="organizationType">
          <a-select v-model="organizationForm.organizationType" style="width: 100%" class="filter-item">
            <a-select-option v-for="item in typesOption" :key="item.key" :value="item.key">
              {{ item.label }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="组织图标" prop="organizationIcon">
          <a-input v-model.trim="organizationForm.organizationIcon" placeholder="输入组织图标" :maxLength="11" />
        </a-form-model-item>
        <a-form-model-item label="组织排序" prop="organizationLevel">
          <a-input v-model.number="organizationForm.organizationLevel" placeholder="输入组织排序" :maxLength="32" />
        </a-form-model-item>
        <a-form-model-item label="组织地区" prop="province">
          <a-cascader
            v-model="organizationForm.areas"
            :options="provinceOptions"
            placeholder="组织地区" />
        </a-form-model-item>
        <a-form-model-item label="详细地址" prop="street">
          <a-input v-model="organizationForm.street" placeholder="详细地址" :maxLength="120" />
        </a-form-model-item>
        <a-form-model-item label="备注信息">
          <a-input v-model.trim="organizationForm.description" :autoSize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="请输入备注信息" />
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
import { STable } from '@/components'
import { fetchOrgList, createOrganization, deleteOrganization, updateOrganization, checkOrganizationName, checkOrganizationKey } from '@/api/system/organization'
import Data from '@/api/pcaa'

export default {
  name: 'OrganizationTable',
  components: { STable },
  filters: {
    typeNameFilter (type) {
      const typeNameMap = {
        1: '总公司',
        2: '分公司',
        3: '事业部',
        4: '业务部'
      }
      return typeNameMap[type]
    }
  },
  data () {
    var validOrganizationName = (rule, value, callback) => {
      var keyData = {
        id: this.organizationForm.id,
        organizationName: value
      }
      checkOrganizationName(keyData).then(response => {
        if (!response.data) {
          callback(new Error('组织名称已存在'))
        } else {
          callback()
        }
      })
    }
    var validOrganizationKey = (rule, value, callback) => {
      var keyData = {
        id: this.organizationForm.id,
        organizationKey: value
      }
      checkOrganizationKey(keyData).then(response => {
        if (!response.data) {
          callback(new Error('组织标识已存在'))
        } else {
          callback()
        }
      })
    }
    return {
      orgLabelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      orgWrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      advanced: false,
      expandAll: false,
      provinceOptions: null,
      props: {
        children: 'children', title: 'organizationName', key: 'id'
      },
      list: [],
      baseList: [],
      rootFlag: false,
      expandTitle: 'organizationTable.organizationName',
      expandName: 'organizationName',
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '添加'
      },
      typesOption: [
        { label: '总公司', key: '1' },
        { label: '分公司', key: '2' },
        { label: '事业部', key: '3' },
        { label: '业务部', key: '4' }
      ],
      dialogPvVisible: false,
      treeQuery: {
        parentId: 0,
        organizationName: '',
        organizationKey: ''
      },
      organizationForm: {
        id: '',
        parentId: 0,
        organizationName: '',
        organizationKey: '',
        organizationType: '1',
        organizationIcon: '',
        organizationLevel: '',
        areas: [],
        street: '',
        children: [], // 必须加，否则新增的节点不显示
        description: ''
      },
      // 表头
      columns: [
        {
          title: '组织名称',
          align: 'left',
          dataIndex: 'organizationName'
        },
        {
          title: '组织标识',
          align: 'center',
          dataIndex: 'organizationKey'
        },
        {
          title: '组织类型',
          align: 'center',
          dataIndex: 'organizationType',
          scopedSlots: { customRender: 'organizationType' }
        },
        {
          title: '组织图标',
          align: 'center',
          dataIndex: 'organizationIcon'
        },
        {
          title: '组织排序',
          align: 'center',
          dataIndex: 'organizationLevel'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '150px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      rules: {
        organizationName: [
          { required: true, message: '请输入组织名称', trigger: 'blur' },
          { min: 2, max: 32, message: '长度在 2 到 32 个字符', trigger: 'blur' },
          { validator: validOrganizationName, trigger: 'blur' }
        ],
        organizationKey: [
          { required: true, message: '请输入组织标识', trigger: 'blur' },
          { min: 2, max: 32, message: '长度在 2 到 32 个字符', trigger: 'blur' },
          { validator: validOrganizationKey, trigger: 'blur' }
        ],
        organizationType: [
          { required: true, message: '请选择组织类型', trigger: 'blur' }
        ],
        organizationIcon: [
          { required: true, message: '请输入组织图标', trigger: 'blur' },
          { min: 2, max: 32, message: '长度在 2 到 32 个字符', trigger: 'blur' }
        ],
        organizationLevel: [
          { required: true, message: '请输入组织排序', trigger: 'blur' }
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
    this.getAreaList()
  },
  methods: {
    resetQuery () {
      this.treeQuery = {
        parentId: 0,
        organizationName: '',
        organizationKey: ''
      }
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    getList () {
      this.listLoading = true
      fetchOrgList(this.treeQuery).then(response => {
        this.list = response.data
        this.baseList = JSON.parse(JSON.stringify(response.data)) // 数组深复制
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
    resetOrganizationForm () {
      this.organizationForm = {
        id: '',
        parentId: 0,
        organizationName: '',
        organizationKey: '',
        organizationType: '1',
        organizationIcon: '',
        organizationLevel: '',
        areas: [],
        street: '',
        children: [], // 必须加，否则新增的节点不显示
        description: ''
      }
    },
    handleCreate (row) {
      this.resetOrganizationForm()
      if (row) {
        this.rootFlag = false
        this.organizationForm.parentId = row.id
      } else {
        this.rootFlag = true
      }
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      if (this.$refs['organizationForm']) {
        this.$nextTick(() => {
          this.$refs['organizationForm'].clearValidate()
        })
      }
    },
    createData () {
      this.$refs['organizationForm'].validate(valid => {
        if (valid) {
          createOrganization(this.organizationForm).then(response => {
            this.dialogFormVisible = false
            this.organizationForm.id = response.data.id
            if (this.rootFlag) {
              this.organizationForm['children'] = null
              this.list.push(this.organizationForm)
              this.baseList.push(
                JSON.parse(JSON.stringify(this.organizationForm))
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
        if (v.id === this.organizationForm.parentId) {
          if (!v.children) {
            v['children'] = null
          }
          this.organizationForm['children'] = null
          v.children.push(JSON.parse(JSON.stringify(this.organizationForm)))
          break
        }
        if (v.children && v.children.length > 0) {
          this.createDataCallBack(v.children)
        }
      }
    },
    handleUpdate (row) {
      this.organizationForm = Object.assign({}, row) // copy obj
      if (!this.organizationForm.areas || this.organizationForm.areas.length === 0) {
        this.organizationForm.areas = [
          this.organizationForm.province,
          this.organizationForm.city,
          this.organizationForm.area
        ]
      }
      // JSON不接受循环对象——引用它们自己的对象
      delete this.organizationForm.parent
      delete this.organizationForm.children
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      if (this.$refs['organizationForm']) {
        this.$refs['organizationForm'].clearValidate()
      }
    },
    updateData () {
      this.$refs['organizationForm'].validate(valid => {
        if (valid) {
          updateOrganization(this.organizationForm).then(() => {
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
        if (v.id === this.organizationForm.id) {
          Object.assign(v, JSON.parse(JSON.stringify(this.organizationForm)))
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
        title: '此操作将永久删除该组织：' + row.organizationName + ', 是否继续?',
        content: '',
        onOk () {
          that.listLoading = true
          deleteOrganization(row.id).then(() => {
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
          this.treeQuery.organizationName === '' &&
          this.treeQuery.organizationKey === ''
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
        var isname = this.treeQuery.organizationName !== ''
        var hasname =
          dataList[i].organizationName.indexOf(
            this.treeQuery.organizationName
          ) >= 0
        var iskey = this.treeQuery.organizationKey !== ''
        var haskey =
          dataList[i].organizationKey.indexOf(this.treeQuery.organizationKey) >=
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
    },
    filter (inputValue, path) {
      return path.some(option => option.organizationName.toLowerCase().indexOf(inputValue.toLowerCase()) > -1)
    }
  }
}
</script>
