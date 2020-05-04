<template>
  <div class="app-container content">
    <div class="filter-container-card">
      <div class="text item">
        <el-form label-width="80px">
          <el-row>
            <el-col :span="6">
              <el-form-item :label="$t('userTable.organization')" prop="selectedOrgOptionsQuery">
                <el-cascader
                  v-model="selectedOrgOptionsQuery"
                  :options="orgList"
                  :props="propsOrg"
                  :show-all-levels="false"
                  :placeholder="$t('userTable.organization')"
                  filterable
                  clearable
                  change-on-select
                  style="width: 180px;"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6" class="line">
              <el-form-item :label="$t('userTable.userName')" prop="userName">
                <el-input v-model="listQuery.userName" :placeholder="$t('userTable.userName')" style="width: 180px;" class="filter-item" maxlength="32" @keyup.enter.native="handleFilter" />
              </el-form-item>
            </el-col>
            <el-col :span="6" class="line">
              <el-form-item :label="$t('userTable.userMobile')" prop="userMobile">
                <el-input v-model="listQuery.userMobile" :placeholder="$t('userTable.userMobile')" style="width: 180px;" class="filter-item" maxlength="11" @keyup.enter.native="handleFilter" />
              </el-form-item>
            </el-col>
            <el-col :span="6" class="line">
              <el-form-item :label="$t('userTable.userEmail')" prop="userEmail">
                <el-input v-model="listQuery.userEmail" :placeholder="$t('userTable.userEmail')" style="width: 180px;" class="filter-item" maxlength="100" @keyup.enter.native="handleFilter" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item :label="$t('table.startDate')" prop="startDate">
                <el-date-picker v-model.trim="listQuery.startDate" :placeholder="$t('table.startDate')" type="date" style="width: 180px;" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item :label="$t('table.endDate')" prop="endDate">
                <el-date-picker v-model.trim="listQuery.endDate" :placeholder="$t('table.endDate')" type="date" style="width: 180px;" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item :label="$t('userTable.roleName')" prop="roleId">
                <el-select v-model="listQuery.roleId" :placeholder="$t('userTable.roleName')" clearable style="width: 180px" class="filter-item">
                  <el-option v-for="item in roleList" :key="item.key" :label="item.roleName" :value="item.id" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item :label="$t('userTable.userStatus')" prop="userStatus">
                <el-select v-model="listQuery.userStatus" :placeholder="$t('userTable.userStatus')" clearable style="width: 180px" class="filter-item">
                  <el-option v-for="item in statusOption" :key="item.key" :label="item.label" :value="item.key" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item style="float:right;">
                <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
                <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">{{ $t('table.add') }}</el-button>
                <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">{{ $t('table.export') }}</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
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
      <el-table-column :label="$t('userTable.id')" align="center" width="65">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('userTable.organization')" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.organizationName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('userTable.userAccount')" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.userAccount }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('userTable.userName')" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('userTable.userMobile')" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.userMobile }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('userTable.userEmail')" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.userEmail }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('userTable.roleName')" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.roleName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('userTable.userSex')" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.userSex | sexNameFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('userTable.createTime')" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('userTable.userStatus')" class-name="status-col" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.userStatus | statusFilter">{{ scope.row.userStatus | statusNameFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column v-if="false" width="110px">
        <template slot-scope="scope">
          <span>{{ scope.row.areas }}</span>
          <span>{{ scope.row.street }}</span>
          <span>{{ scope.row.roleIds }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.actions')" align="center" width="310" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{ $t('table.edit') }}</el-button>
          <el-button plain type="primary" size="mini" style="width:68px;" @click="handleDataPermission(scope.row)">{{ $t('userTable.permissionEdit') }}</el-button>
          <el-button v-if="scope.row.userStatus!='1'" size="mini" type="success" @click="handleModifyStatus(scope.row,'1')">{{ $t('userTable.enable') }}
          </el-button>
          <el-button v-if="scope.row.userStatus!='0' && scope.row.userStatus!='2'" size="mini" @click="handleModifyStatus(scope.row,'0')">{{ $t('userTable.disable') }}
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">{{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :close-on-click-modal="false">
      <el-form ref="userForm" :model="userForm" :rules="rules" label-width="100px" class="userForm" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('userTable.organization')" prop="userAccount">
          <el-cascader
            v-model="selectedOrgOptions"
            :options="orgList"
            :props="propsOrg"
            :show-all-levels="false"
            :placeholder="$t('userTable.organization')"
            filterable
            clearable
            change-on-select
            style="width: 100%;"
          />
        </el-form-item>
        <el-form-item :label="$t('userTable.userAccount')" prop="userAccount">
          <el-input v-model="userForm.userAccount" placeholder="输入用户账号" maxlength="32" />
        </el-form-item>
        <el-form-item :label="$t('userTable.userNickName')" prop="userNickName">
          <el-input v-model="userForm.userNickName" placeholder="输入用户昵称" maxlength="32" />
        </el-form-item>
        <el-form-item :label="$t('userTable.userName')" prop="userName">
          <el-input v-model="userForm.userName" placeholder="输入用户姓名" maxlength="32" />
        </el-form-item>
        <el-form-item :label="$t('userTable.userMobile')" prop="userMobile">
          <el-input v-model="userForm.userMobile" placeholder="输入用户手机号码" maxlength="11" />
        </el-form-item>
        <el-form-item :label="$t('userTable.userEmail')" prop="userEmail">
          <el-input v-model="userForm.userEmail" placeholder="输入用户电子邮箱" maxlength="32" />
        </el-form-item>
        <el-form-item :label="$t('userTable.roleName')" prop="roleId">
          <el-select v-model="userForm.roleIds" class="filter-item" multiple placeholder="选择用户角色" style="width: 100%;">
            <el-option v-for="item in roleList" :key="item.key" :label="item.roleName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('userTable.area')" prop="areas">
          <el-cascader v-model="userForm.areas" :options="provinceOptions" :props="props" clearable filterable change-on-select style="width:100%;" />
        </el-form-item>
        <el-form-item :label="$t('userTable.street')" prop="street">
          <el-input v-model="userForm.street" placeholder="详细地址" maxlength="120" />
        </el-form-item>
        <el-form-item :label="$t('userTable.userSex')" prop="userSex">
          <el-radio-group v-model="userForm.userSex">
            <el-radio :label="1">男性</el-radio>
            <el-radio :label="0">女性</el-radio>
            <el-radio :label="2">保密</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('userTable.userStatus')" prop="userStatus">
          <el-radio-group v-model="userForm.userStatus">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="2">未激活</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('userTable.description')">
          <el-input v-model="userForm.description" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="请输入备注信息" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">{{ $t('table.confirm') }}</el-button>
        <el-button v-else type="primary" @click="updateData">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogDataPermissionVisible" title="设置用户数据权限" :close-on-click-modal="false">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>组织机构列表</span>
        </div>
        <div class="text item">
          <el-tree
            ref="tree"
            :data="orgList"
            :props="propsOrg"
            :default-expanded-keys="userCheckOrgPermission"
            :default-checked-keys="userCheckOrgPermission"
            show-checkbox
            check-strictly
            node-key="id"
            class="filter-container-card"
            highlight-current
            @check-change="computeOrgPermission"
          />
        </div>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogDataPermissionVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button type="primary" @click="updateDataPermission">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchList, createUser, deleteUser, updateUser, updateUserStatus, fetchRoleList, updateUserDataPermission, checkUserAccount, checkUserMobile, checkUserEmail, checkUserNickName } from '@/api/system/user'
import { fetchOrgList } from '@/api/system/organization'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'
import Data from '@/api/pcaa'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'UserTable',
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
        2: '未激活',
        0: '禁用'
      }
      return statusNameMap[status]
    },
    sexNameFilter(sex) {
      const sexNameMap = {
        1: '男',
        2: '保密',
        0: '女'
      }
      return sexNameMap[sex]
    }
  },
  data() {
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
      tableKey: 0,
      roleList: null,
      provinceOptions: null,
      props: {
        children: 'children'
      },
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        current: 1,
        size: 20,
        userName: '',
        userMobile: '',
        userEmail: '',
        roleIds: [],
        organizationId: '',
        userStatus: ''
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
        label: 'organizationName'
      },
      orgList: [],
      userCheckOrgPermission: [],
      addOrgPermission: [],
      removeOrgPermission: []
    }
  },
  created() {
    this.getList()
    this.getOrgList()
    this.getRoleList()
    this.getAreaList()
  },
  methods: {
    getList() {
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
    getOrgList() {
      this.listLoading = true
      fetchOrgList({ parentId: 0 }).then(response => {
        this.orgList = response.data
        this.listLoading = false
      })
    },
    selectOrgListByLastId(orgList, lastId) {
      // 递归查询机构父机构，用于展示已选中的机构
      var orgStr = ''
      if (orgList) {
        for (var org of orgList) {
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
    computeOrgPermission(item, node) {
      if (node) {
        // 如果原先不存在，则添加到新增列表
        if (this.userCheckOrgPermission.indexOf(item.id) === -1) {
          this.addOrgPermission.push(item.id)
        }
        // 如果在删除列表中，则从删除列表中删除
        var removeIndex = this.removeOrgPermission.indexOf(item.id)
        if (removeIndex > -1) {
          this.removeOrgPermission.splice(removeIndex, 1)
        }
      } else {
        // 如果原先存在，则添加到删除列表
        if (this.userCheckOrgPermission.indexOf(item.id) > -1) {
          this.removeOrgPermission.push(item.id)
        }
        // 如果在新增列表中，则从新增列表中删除
        var addIndex = this.addOrgPermission.indexOf(item.id)
        if (addIndex > -1) {
          this.addOrgPermission.splice(addIndex, 1)
        }
      }
    },
    getRoleList() {
      this.listLoading = true
      fetchRoleList().then(response => {
        this.roleList = response.data
        this.listLoading = false
      })
    },
    getAreaList() {
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
    handleFilter() {
      this.listQuery.current = 1
      this.getList()
    },
    resetUserForm() {
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
    resetDataPermissionForm() {
      this.dataPermissionForm = {
        userId: '',
        addDataPermissions: [],
        removeDataPermissions: []
      }
    },
    handleCreate() {
      this.resetUserForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['userForm'].clearValidate()
      })
    },
    createData() {
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
            this.$message({
              message: '创建成功',
              type: 'success'
            })
          })
        }
      })
    },
    handleUpdate(row) {
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
    updateData() {
      if (this.selectedOrgOptions.length > 0) {
        this.userForm.organizationId = this.selectedOrgOptions[this.selectedOrgOptions.length - 1]
      } else {
        this.userForm.organizationId = ''
      }
      this.$refs['userForm'].validate(valid => {
        if (valid) {
          updateUser(this.userForm).then(() => {
            for (const v of this.list) {
              if (v.id === this.userForm.id) {
                const index = this.list.indexOf(v)
                var arrRoleNames = []
                for (const role of this.roleList) {
                  for (var roleId of this.userForm.roleIds) {
                    if (role.id === roleId) {
                      arrRoleNames.push(role.roleName)
                    }
                  }
                }
                this.userForm.roleName = arrRoleNames.join()
                this.list.splice(index, 1, this.userForm)
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
    handleDataPermission(row) {
      this.dialogStatus = 'update'
      this.dialogDataPermissionVisible = true
      this.resetDataPermissionForm()
      this.userForm = Object.assign({}, row)
      if (this.$refs['tree']) {
        this.$refs['tree'].setCheckedKeys([])
      }
      this.addOrgPermission = []
      this.removeOrgPermission = []
      if (this.userForm.dataPermission) {
        this.userCheckOrgPermission = this.userForm.dataPermission.split(',').map(Number)
      } else {
        this.userCheckOrgPermission = []
      }
      this.dataPermissionForm.userId = this.userForm.id
    },
    updateDataPermission() {
      this.dataPermissionForm.addDataPermissions = this.addOrgPermission
      this.dataPermissionForm.removeDataPermissions = this.removeOrgPermission
      updateUserDataPermission(this.dataPermissionForm).then(() => {
        this.handleFilter()
        this.dialogDataPermissionVisible = false
        this.$message({
          message: '更新成功',
          type: 'success'
        })
      })
    },
    handleDelete(row) {
      this.$confirm(
        '此操作将永久删除该用户：' + row.userName + ', 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          this.listLoading = true
          deleteUser(row.id).then(() => {
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
      updateUserStatus(row.id, status).then(() => {
        this.listLoading = false
        row.userStatus = status
        this.$message({
          message: '状态修改成功',
          type: 'success'
        })
      })
    },
    handleDownload() {
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
    formatJson(filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          if (j === 'createTime') {
            return parseTime(v[j])
          } else if (j === 'userSex') {
            return this.$options.filters['sexNameFilter'](v[j])
          } else if (j === 'userStatus') {
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
