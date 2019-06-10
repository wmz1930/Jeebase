<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.id" :placeholder="$t('roleTable.id')" style="width: 150px;" class="filter-item" maxlength="32" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.roleName" :placeholder="$t('roleTable.roleName')" style="width: 150px;" class="filter-item" maxlength="32" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.roleKey" :placeholder="$t('roleTable.roleKey')" style="width: 150px;" class="filter-item" maxlength="32" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.roleStatus" :placeholder="$t('roleTable.roleStatus')" clearable style="width: 150px" class="filter-item">
        <el-option v-for="item in statusOption" :key="item.key" :label="item.label" :value="item.key" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">{{ $t('table.add') }}</el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">{{ $t('table.export') }}</el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column :label="$t('roleTable.id')" align="center" width="65">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('roleTable.roleName')" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.roleName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('roleTable.roleKey')" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.roleKey }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('roleTable.roleLevel')" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.roleLevel }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('roleTable.createTime')" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('roleTable.roleStatus')" class-name="status-col" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.roleStatus | statusFilter">{{ scope.row.roleStatus | statusNameFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('roleTable.description')" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.description }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.actions')" align="center" width="300" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{ $t('table.edit') }}</el-button>
          <el-button type="primary" size="mini" @click="handleUpdateResource(scope.row)">{{ $t('roleTable.resource') }}</el-button>
          <el-button v-if="scope.row.roleStatus!='1'" size="mini" type="success" @click="handleModifyStatus(scope.row,'1')">{{ $t('roleTable.enable') }}
          </el-button>
          <el-button v-if="scope.row.roleStatus!='0'" size="mini" @click="handleModifyStatus(scope.row,'0')">{{ $t('roleTable.disable') }}
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">{{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="roleForm" :model="roleForm" :rules="rules" label-width="100px" class="roleForm" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('roleTable.roleName')" prop="roleName">
          <el-input v-model="roleForm.roleName" placeholder="输入角色名称" maxlength="32" />
        </el-form-item>
        <el-form-item :label="$t('roleTable.roleKey')" prop="roleKey">
          <el-input v-model="roleForm.roleKey" placeholder="输入角色标识" maxlength="32" />
        </el-form-item>
        <el-form-item :label="$t('roleTable.roleLevel')" prop="roleLevel">
          <el-input v-model="roleForm.roleLevel" placeholder="输入角色级别" maxlength="5" />
        </el-form-item>
        <el-form-item :label="$t('roleTable.roleStatus')" prop="roleStatus">
          <el-radio-group v-model="roleForm.roleStatus">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('roleTable.description')">
          <el-input v-model="roleForm.description" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="请输入备注信息" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">{{ $t('table.confirm') }}</el-button>
        <el-button v-else type="primary" @click="updateData">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogResourceVisible" title="配置资源">
      <el-input
        v-model="filterText"
        placeholder="输入关键字进行过滤"
      />
      <el-tree
        ref="tree"
        :data="resourceTree"
        :filter-node-method="filterNode"
        :default-expanded-keys="resourceTreeExpandedKeys"
        :default-checked-keys="resourceTreeCheckedKeys"
        :props="defaultProps"
        show-checkbox
        node-key="id"
        default-expand-all
        check-strictly
        style="margin-top: 20px;"
      />
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogResourceVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button type="primary" @click="updateRoleResource">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchList, createRole, deleteRole, updateRole, updateRoleStatus, queryRoleResource, updateRoleResources, checkRoleName, checkRoleKey } from '@/api/system/role'
import { fetchResourceList } from '@/api/system/resource'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'RoleTable',
  components: { Pagination },
  directives: {
    waves
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: 'success',
        2: 'info',
        0: 'danger'
      }
      return statusMap[status]
    },
    statusNameFilter(status) {
      const statusNameMap = {
        1: '启用',
        0: '禁用'
      }
      return statusNameMap[status]
    }
  },
  data() {
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
      oldResourceList: [],
      defaultProps: {
        children: 'children',
        label: 'resourceName'
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs['tree'].filter(val)
    }
  },
  created() {
    this.getList()
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true
      return data.resourceName.indexOf(value) !== -1
    },
    getList() {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        this.list = response.data
        this.total = response.count
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.current = 1
      this.getList()
    },
    resetroleForm() {
      this.roleForm = {
        id: '',
        roleName: '',
        roleKey: '',
        roleLevel: '',
        roleStatus: 1,
        description: ''
      }
    },
    handleCreate() {
      this.resetroleForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['roleForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['roleForm'].validate(valid => {
        if (valid) {
          createRole(this.roleForm).then(() => {
            this.dialogFormVisible = false
            this.handleFilter()
            this.$message({
              message: '创建成功',
              type: 'success'
            })
          })
        }
      })
    },
    handleUpdate(row) {
      this.roleForm = Object.assign({}, row) // copy obj
      this.roleForm.roleStatus = parseInt(this.roleForm.roleStatus)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['roleForm'].clearValidate()
      })
    },
    updateData() {
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
            this.$message({
              message: '更新成功',
              type: 'success'
            })
          })
        }
      })
    },
    handleDelete(row) {
      this.$confirm(
        '此操作将永久删除该角色：' + row.roleName + ', 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          this.listLoading = true
          deleteRole(row.id).then(() => {
            this.listLoading = false
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            const index = this.list.indexOf(row)
            this.list.splice(index, 1)
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    handleModifyStatus(row, status) {
      this.listLoading = true
      updateRoleStatus(row.id, status).then(() => {
        this.listLoading = false
        row.roleStatus = status
        this.$message({
          message: '状态修改成功',
          type: 'success'
        })
      })
    },
    handleUpdateResource(row) {
      this.currentRole = row.id
      this.resourceTreeExpandedKeys = []
      this.resourceTreeCheckedKeys = []
      this.resourceTree = []
      this.resourceData.addResources = []
      this.resourceData.delResources = []
      this.oldResourceList = []
      this.resetChecked()
      this.listLoading = true
      fetchResourceList(this.treeQuery).then(response => {
        this.resourceTree = response.data
        this.dialogResourceVisible = true
        this.listLoading = false
        queryRoleResource(row.id).then(response => {
          if (response.data && response.data.length > 0) {
            for (var i = 0; i < response.data.length; i++) {
              this.resourceTreeCheckedKeys[i] = response.data[i].resourceId
              this.resourceTreeExpandedKeys[i] = response.data[i].resourceId
              this.oldResourceList[i] = response.data[i].resourceId
            }
            this.setCheckedKeys(this.resourceTreeCheckedKeys)
          }

          this.listLoading = false
        })
      })
    },
    updateRoleResource() {
      this.listLoading = true
      var ids = []
      var keysChecked = this.$refs['tree'].getCheckedKeys()
      var cLength = 0
      if (keysChecked && keysChecked.length > 0) {
        cLength = keysChecked.length
        for (var i = 0; i < cLength; i++) {
          ids[i] = keysChecked[i]
        }
      }

      var keysHalf = this.$refs['tree'].getHalfCheckedKeys()
      if (keysHalf && keysHalf.length > 0) {
        for (var j = 0; j < keysHalf.length; j++) {
          ids[cLength + j] = keysHalf[j]
        }
      }
      var that = this

      var addResourceIds = ids.filter(function(v) { return that.oldResourceList.indexOf(v) === -1 })

      var delResourceIds = that.oldResourceList.filter(function(v) { return ids.indexOf(v) === -1 })

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
        this.$message({
          message: '角色修改成功',
          type: 'success'
        })
      })
    },
    setCheckedKeys(keys) {
      if (this.$refs['tree']) {
        this.$refs['tree'].setCheckedKeys(keys)
      }
    },
    resetChecked() {
      if (this.$refs['tree']) {
        this.$refs['tree'].setCheckedKeys([])
      }
    },
    handleDownload() {
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
    formatJson(filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          if (j === 'createTime') {
            return parseTime(v[j])
          } else if (j === 'roleStatus') {
            return this.$options.filters['statusNameFilter'](v[j])
          } else {
            return v[j]
          }
        })
      )
    }
  }
}
</script>
