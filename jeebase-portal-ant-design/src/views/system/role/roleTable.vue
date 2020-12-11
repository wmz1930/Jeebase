<template>
  <a-card :bordered="false" class="content">
    <div class="table-page-search-wrapper">
      <a-form-model layout="inline">
        <a-row :gutter="48">
          <a-col :md="6" :sm="24">
            <a-form-model-item label="序号">
              <a-input
                v-model.trim="listQuery.id"
                placeholder="序号"
                :max-length="32"
                @keyup.enter.native="handleFilter" />
            </a-form-model-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-model-item label="角色名称">
              <a-input
                v-model.trim="listQuery.roleName"
                placeholder="角色名称"
                :max-length="11"
                @keyup.enter.native="handleFilter" />
            </a-form-model-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-model-item label="角色标识">
              <a-input
                v-model.trim="listQuery.roleKey"
                placeholder="角色标识"
                :max-length="100"
                @keyup.enter.native="handleFilter" />
            </a-form-model-item>
          </a-col>
          <template v-if="advanced">
            <a-col :md="6" :sm="24">
              <a-form-model-item label="角色状态">
                <a-select v-model="listQuery.roleStatus" placeholder="角色状态" allow-clear show-search :filter-option="filterOption">
                  <a-select-option v-for="item in statusOption" :key="item.key" :value="item.key">
                    {{ item.label }}
                  </a-select-option>
                </a-select>
              </a-form-model-item>
            </a-col>
          </template>
          <a-col :md="!advanced && 6 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
              <a-button type="primary" @click="handleFilter">查询</a-button>
              <a-button style="margin-left: 8px" @click="resetQuery">重置</a-button>
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
      ref="roleTable"
      size="default"
      bordered
      :rowKey="row=>row.id"
      :columns="columns"
      :data="loadData"
      showPagination="auto"
      :pagination="rolePagination"
      :rowSelection="{ selectedRowKeys: this.selectedRowKeys, onChange: this.onSelectChange }"
    >
      <span slot="status" slot-scope="text, record">
        <a-tag :color="record.roleStatus | statusFilter">{{ record.roleStatus | statusNameFilter }}</a-tag>
      </span>
      <span slot="createTime" slot-scope="text, record">
        <span>{{ record.createTime | moment }}</span>
      </span>
      <span slot="action" slot-scope="text, record">
        <a @click="handleUpdate(record)">编辑</a>
        <a-divider type="vertical" />
        <a href="javascript:;" @click="handleUpdateResource(record)">资源</a>
        <a-divider type="vertical" />
        <a-dropdown>
          <a class="ant-dropdown-link">
            更多 <a-icon type="down" />
          </a>
          <a-menu slot="overlay">
            <a-menu-item>
              <a href="javascript:;" v-if="record.roleStatus!='1'" size="mini" type="success" @click="handleModifyStatus(record,'1')">启用
              </a>
              <a href="javascript:;" v-if="record.roleStatus!='0' && record.roleStatus!='2'" size="mini" @click="handleModifyStatus(record,'0')">禁用
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
        ref="roleForm"
        :model="roleForm"
        :rules="rules"
        :label-col="roleLabelCol"
        :wrapper-col="roleWrapperCol">
        <a-form-model-item label="角色名称" prop="roleName">
          <a-input v-model="roleForm.roleName" placeholder="输入角色名称" :maxLength="32" />
        </a-form-model-item>
        <a-form-model-item label="角色标识" prop="roleKey">
          <a-input v-model="roleForm.roleKey" placeholder="输入角色标识" :maxLength="32" />
        </a-form-model-item>
        <a-form-model-item label="角色级别" prop="roleLevel">
          <a-input v-model="roleForm.roleLevel" placeholder="输入角色级别" :maxLength="5" />
        </a-form-model-item>
        <a-form-model-item label="角色状态" prop="roleStatus">
          <a-radio-group v-model="roleForm.roleStatus" name="roleStatus">
            <a-radio :value="1">启用</a-radio>
            <a-radio :value="0">禁用</a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="备注信息">
          <a-input v-model="roleForm.description" :autoSize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="请输入备注信息" />
        </a-form-model-item>
      </a-form-model>
      <div slot="footer" class="dialog-footer">
        <a-button @click="dialogFormVisible = false">取消</a-button>
        <a-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</a-button>
        <a-button v-else type="primary" @click="updateData">修改</a-button>
      </div>
    </a-modal>

    <a-modal v-model="dialogResourceVisible" :maskClosable="false" :destroy-on-close="true" title="配置角色资源">
      <a-card class="box-card">
        <a-input-search style="margin-bottom: 8px" placeholder="输入关键字进行过滤" @change="onTreeChange" />
        <a-tree
          ref="resourceTree"
          checkable
          :check-strictly="true"
          v-model="resourceTreeCheckedKeys"
          :tree-data="resourceTreeData"
          :replace-fields="treeProps"
          :default-expanded-keys="resourceTreeExpandedKeys"
          :expanded-keys="expandedKeys"
          :auto-expand-parent="autoExpandParent"
          @expand="onTreeExpand"
          @check="computeRoleResources"
        >
          <template slot="resourceName" slot-scope="{ resourceName }">
            <span v-if="resourceName.indexOf(searchValue) > -1">
              {{ resourceName.substr(0, resourceName.indexOf(searchValue)) }}
              <span style="color: #f50">{{ searchValue }}</span>
              {{ resourceName.substr(resourceName.indexOf(searchValue) + searchValue.length) }}
            </span>
            <span v-else>{{ resourceName }}</span>
          </template>
        </a-tree>
      </a-card>
      <div slot="footer" class="dialog-footer">
        <a-button @click="dialogResourceVisible = false">取消</a-button>
        <a-button type="primary" @click="updateRoleResource">确定</a-button>
      </div>
    </a-modal>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { fetchList, createRole, deleteRole, updateRole, batchDeleteRole, updateRoleStatus, queryRoleResource, updateRoleResources, checkRoleName, checkRoleKey } from '@/api/system/role'
import { fetchResourceList } from '@/api/system/resource'
import moment from 'moment'

export default {
  name: 'RoleTable',
  components: { moment, STable },
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
        0: '禁用'
      }
      return statusNameMap[status]
    }
  },
  data () {
    var validRoleName = (rule, value, callback) => {
      var keyData = {
        id: this.roleForm.id,
        roleName: value
      }
      checkRoleName(keyData).then(response => {
        if (!response.data) {
          callback(new Error('角色名称已存在'))
        } else {
          callback()
        }
      })
    }
    var validRoleKey = (rule, value, callback) => {
      var keyData = {
        id: this.roleForm.id,
        roleKey: value
      }
      checkRoleKey(keyData).then(response => {
        if (!response.data) {
          callback(new Error('角色标识已存在'))
        } else {
          callback()
        }
      })
    }
    return {
      advanced: false,
      currentRole: '',
      filterText: '',
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        current: 1,
        size: 20,
        id: '',
        roleName: '',
        roleKey: '',
        roleStatus: ''
      },
      treeQuery: {
        parentId: 0
      },
      resourceData: {
        roleId: '',
        addResources: [],
        delResources: []
      },
      expandedKeys: [],
      checkedKeys: [],
      searchValue: '',
      autoExpandParent: true,
      statusOption: [{ label: '启用', key: '1' }, { label: '禁用', key: '0' }],
      dialogFormVisible: false,
      dialogResourceVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '添加'
      },
      dialogPvVisible: false,
      roleForm: {
        id: '',
        roleName: '',
        roleKey: '',
        roleLevel: '',
        roleStatus: 1,
        description: ''
      },
      // 表头
      columns: [
        {
          title: '序号',
          align: 'center',
          dataIndex: 'id'
        },
        {
          title: '角色名称',
          align: 'center',
          dataIndex: 'roleName'
        },
        {
          title: '角色标识',
          align: 'center',
          dataIndex: 'roleKey'
        },
        {
          title: '角色级别',
          align: 'center',
          dataIndex: 'roleLevel'
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
          dataIndex: 'roleStatus',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '描述',
          align: 'center',
          dataIndex: 'description'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '180px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      rules: {
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur' },
          { validator: validRoleName, trigger: 'blur' }
        ],
        roleKey: [
          { required: true, message: '请输入角色标识', trigger: 'blur' },
          { min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur' },
          { validator: validRoleKey, trigger: 'blur' }
        ],
        roleStatus: [
          { required: true, message: '请选择用户状态', trigger: 'change' }
        ],
        description: [
          { required: true, message: '请填写备注信息', trigger: 'blur' }
        ]
      },
      downloadLoading: false,
      resourceTreeExpandedKeys: [],
      resourceTreeCheckedKeys: [],
      resourceTree: [],
      resourceTreeData: [],
      resourceDataList: [],
      oldResourceList: [],
      treeProps: {
        children: 'children', title: 'resourceName', key: 'id'
      },
      roleLabelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      roleWrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      selectedRowKeys: [],
      selectedRows: [],
      rolePagination: {
        defaultPageSize: 10,
        showQuickJumper: true,
        defaultCurrent: 1,
        showTotal: (total, range) => `共 ${total} 条`
      },
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return fetchList(Object.assign(parameter, this.listQuery))
          .then(res => {
            this.list = res.data
            return res
          })
      }
    }
  },
  watch: {
    // filterText (val) {
    //   this.$refs['roleTree'].filter(val)
    // }
  },
  created () {
    this.getList()
  },
  methods: {
    resetQuery () {
      this.listQuery = {
        current: 1,
        size: 20,
        id: '',
        roleName: '',
        roleKey: '',
        roleStatus: ''
      }
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    getList () {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        this.list = response.data
        this.total = response.count
        this.listLoading = false
      })
    },
    handleFilter () {
      this.$refs.roleTable.refresh(true)
    },
    handleTableRefresh () {
      this.$refs.roleTable.refresh()
    },
    resetroleForm () {
      this.roleForm = {
        id: '',
        roleName: '',
        roleKey: '',
        roleLevel: '',
        roleStatus: 1,
        description: ''
      }
    },
    handleCreate () {
      this.resetroleForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['roleForm'].clearValidate()
      })
    },
    createData () {
      this.$refs['roleForm'].validate(valid => {
        if (valid) {
          createRole(this.roleForm).then(() => {
            this.dialogFormVisible = false
            this.handleFilter()
            this.$message.success('创建成功')
          })
        }
      })
    },
    handleUpdate (row) {
      this.roleForm = Object.assign({}, row) // copy obj
      this.roleForm.roleStatus = parseInt(this.roleForm.roleStatus)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['roleForm'].clearValidate()
      })
    },
    updateData () {
      this.$refs['roleForm'].validate(valid => {
        if (valid) {
          updateRole(this.roleForm).then(() => {
            for (const v of this.list) {
              if (v.id === this.roleForm.id) {
                const index = this.list.indexOf(v)
                this.list.splice(index, 1, this.roleForm)
                break
              }
            }
            this.dialogFormVisible = false
            this.$message.success('更新成功')
          })
        }
      })
    },
    handleDelete (row) {
      var that = this
      this.$confirm({
        title: '此操作将永久删除该角色：' + row.roleName + ', 是否继续?',
        content: '',
        onOk () {
          that.listLoading = true
          deleteRole(row.id).then(() => {
            that.listLoading = false
            that.$message.success('删除成功!')
            that.handleTableRefresh()
          })
        },
        onCancel () {
          that.$message.info('已取消删除')
        }
      })
    },
    handleBatchDelete (row) {
      var roleList = this.selectedRows.map(function (n) {
        return n.roleName
      })
      var that = this
      this.$confirm({
        title: '以下角色将被全部删除，是否继续?',
        content: roleList.join(','),
        onOk () {
          that.listLoading = true
          batchDeleteRole(that.selectedRowKeys).then(() => {
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
      updateRoleStatus(row.id, status).then(() => {
        this.listLoading = false
        row.roleStatus = status
        this.$message.success('状态修改成功')
      })
    },
    handleUpdateResource (row) {
      var that = this
      this.currentRole = row.id
      this.resourceTreeExpandedKeys = []
      this.resourceTreeCheckedKeys = []
      this.resourceTree = []
      this.resourceTreeData = []
      this.resourceData.addResources = []
      this.resourceData.delResources = []
      this.oldResourceList = []
      this.expandedKeys = []
      this.checkedKeys = []
      this.searchValue = ''
      this.listLoading = true
      that.$nextTick(() => {
        fetchResourceList(this.treeQuery).then(response => {
          this.dialogResourceVisible = true
          var resourceListStr = JSON.stringify(response.data)
          var dataResourceList = JSON.parse(resourceListStr.replace(/"isLeaf":1/g, '"isLeaf":true').replace(/"isLeaf":0/g, '"isLeaf":false'))

          that.generateList(dataResourceList)

          that.resourceTree = dataResourceList
          that.resourceTreeData = dataResourceList

          that.listLoading = false

          queryRoleResource(row.id).then(response => {
            if (response.data && response.data.length > 0) {
              for (var i = 0; i < response.data.length; i++) {
                that.resourceTreeCheckedKeys[i] = response.data[i].resourceId
                that.resourceTreeExpandedKeys[i] = response.data[i].resourceId
                that.expandedKeys[i] = response.data[i].resourceId
                that.oldResourceList[i] = response.data[i].resourceId
              }
              that.resourceTreeCheckedKeys = that.resourceTreeCheckedKeys.map(Number)
              that.resourceTreeExpandedKeys = that.resourceTreeExpandedKeys.map(Number)
              that.expandedKeys = that.expandedKeys.map(Number)
              that.oldResourceList = that.oldResourceList.map(Number)
              that.checkedKeys = JSON.parse(JSON.stringify(that.expandedKeys))
            }
            that.listLoading = false
          })
        })
      })
    },
    updateRoleResource () {
      this.listLoading = true
      var ids = []
      var keysChecked = this.checkedKeys
      var cLength = 0
      if (keysChecked && keysChecked.length > 0) {
        cLength = keysChecked.length
        for (var i = 0; i < cLength; i++) {
          ids[i] = keysChecked[i]
        }
      }

      var that = this

      var addResourceIds = ids.filter(function (v) { return that.oldResourceList.indexOf(v) === -1 })

      var delResourceIds = that.oldResourceList.filter(function (v) { return ids.indexOf(v) === -1 })

      if (addResourceIds && addResourceIds.length > 0) {
        for (var k = 0; k < addResourceIds.length; k++) {
          this.resourceData.addResources[k] = { roleId: this.currentRole, resourceId: addResourceIds[k] }
        }
      }

      if (delResourceIds && delResourceIds.length > 0) {
        for (var q = 0; q < delResourceIds.length; q++) {
          this.resourceData.delResources[q] = { roleId: this.currentRole, resourceId: delResourceIds[q] }
        }
      }

      this.resourceData.roleId = this.currentRole
      updateRoleResources(this.resourceData).then(response => {
        this.dialogResourceVisible = false
        this.listLoading = false
        this.$message.success('角色资源修改成功')
      })
    },
    handleDownload () {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = [
          '序号',
          '角色名称',
          '角色标识',
          '角色级别',
          '创建时间',
          '角色状态'
        ]
        const filterVal = [
          'id',
          'roleName',
          'roleKey',
          'roleLevel',
          'createTime',
          'roleStatus'
        ]
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '角色列表'
        })
        this.downloadLoading = false
      })
    },
    formatJson (filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          if (j === 'createTime') {
            return moment(v[j])
          } else if (j === 'roleStatus') {
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
    },
    generateList (data) {
      for (let i = 0; i < data.length; i++) {
        const node = data[i]
        node.scopedSlots = { title: 'resourceName' }
        const key = node.id
        this.resourceDataList.push({ key, title: node.resourceName })
        if (node.children) {
          this.generateList(node.children)
        }
      }
    },
    getParentKey (key, tree) {
      let parentKey
      for (let i = 0; i < tree.length; i++) {
        const node = tree[i]
        if (node.children) {
          if (node.children.some(item => item.id === key)) {
            parentKey = node.id
          } else if (this.getParentKey(key, node.children)) {
            parentKey = this.getParentKey(key, node.children)
          }
        }
      }
      return parentKey
    },
    onTreeExpand (expandedKeys) {
      this.expandedKeys = expandedKeys
      this.autoExpandParent = false
    },
    onTreeChange (e) {
      const value = e.target.value
      const expandedKeys = this.resourceDataList
        .map(item => {
          if (item.title.indexOf(value) > -1) {
            return this.getParentKey(item.key, this.resourceTreeData)
          }
          return null
        })
        .filter((item, i, self) => item && self.indexOf(item) === i)
      Object.assign(this, {
        expandedKeys,
        searchValue: value,
        autoExpandParent: true
      })
    },
    computeRoleResources (item, e) {
      this.checkedKeys = item.checked
      var halfCheckedKeys = item.halfChecked
      if (halfCheckedKeys) {
        this.checkedKeys = this.checkedKeys.concat(halfCheckedKeys)
      }
    }
  }
}
</script>
