<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model.trim="treeQuery.resourceName" :placeholder="$t('resourceTable.resourceName')" style="width: 150px;" class="filter-item" maxlength="32" />
      <el-input v-model.trim="treeQuery.resourceKey" :placeholder="$t('resourceTable.resourceKey')" style="width: 150px;" class="filter-item" maxlength="11" />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="searchTreeData()">{{ $t('table.search') }}</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate(false)">{{ $t('table.add') }}</el-button>
    </div>

    <el-table ref="resourceTree" :data="list" row-key="id" border>
      <el-table-column :label="$t('resourceTable.resourceName')">
        <template slot-scope="scope">
          <span :id="scope.row.id">{{ scope.row.resourceName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('resourceTable.resourceKey')">
        <template slot-scope="scope">
          <span :id="scope.row.resourceKey">{{ scope.row.resourceKey }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('resourceTable.resourceType')">
        <template slot-scope="scope">
          <span>{{ scope.row.resourceType | typeNameFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('resourceTable.resourceUrl')">
        <template slot-scope="scope">
          <span>{{ scope.row.resourceUrl }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('resourceTable.resourceIcon')">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.resourceIcon" />
        </template>
      </el-table-column>
      <el-table-column :label="$t('resourceTable.resourceLevel')">
        <template slot-scope="scope">
          <span>{{ scope.row.resourceLevel }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" width="300">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleCreate(scope.row)">{{ $t('table.add') }}</el-button>
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{ $t('table.edit') }}</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)">{{ $t('table.delete') }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="resourceForm" :model="resourceForm" :rules="rules" label-width="100px" class="resourceForm" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('resourceTable.resourceName')" prop="resourceName">
          <el-input v-model.trim="resourceForm.resourceName" placeholder="权限或者菜单的名称" maxlength="32" />
        </el-form-item>
        <el-form-item :label="$t('resourceTable.resourceKey')" prop="resourceKey">
          <el-input v-model.trim="resourceForm.resourceKey" placeholder="系统用来判断权限的唯一key" maxlength="32" />
        </el-form-item>
        <el-form-item :label="$t('resourceTable.resourceType')" prop="resourceType">
          <el-radio-group v-model="resourceForm.resourceType">
            <el-radio :label="1">模块</el-radio>
            <el-radio :label="2">菜单</el-radio>
            <el-radio :label="4">接口</el-radio>
            <el-radio :label="3">按钮</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('resourceTable.resourceIcon')" prop="resourceIcon">
          <el-input v-model.trim="resourceForm.resourceIcon" placeholder="菜单的图标，不是菜单可以不填" maxlength="32" />
        </el-form-item>
        <el-form-item :label="$t('resourceTable.resourcePath')" prop="resourcePath">
          <el-tooltip class="item" effect="dark" content="1、单页面路由地址 2、外链地址以http://或https://开头" placement="right">
            <el-input v-model.trim="resourceForm.resourcePath" placeholder="浏览器地址栏显示的url" />
          </el-tooltip>
        </el-form-item>
        <el-form-item :label="$t('resourceTable.resourceUrl')" prop="resourceUrl">
          <el-tooltip class="item" effect="dark" content="1、一级菜单填Layout 2、包含子菜单的二级菜单填nested 3、最后子菜单填写页面对应路径 4、接口填写请求路径" placement="right">
            <el-input v-model.trim="resourceForm.resourceUrl" placeholder="菜单对应前台页面的路径" />
          </el-tooltip>
        </el-form-item>
        <el-form-item :label="$t('resourceTable.resourceLevel')" prop="resourceLevel">
          <el-input v-model.number="resourceForm.resourceLevel" placeholder="菜单排序" maxlength="32" />
        </el-form-item>
        <el-form-item v-show="resourceForm.resourceType === 2" :label="$t('resourceTable.resourcePageName')" prop="resourcePageName">
          <el-tooltip class="item" effect="dark" content="如果前端页面开启了tagView，一定要跟页面中定义的name值保持名称一致，否则不能keep-alive" placement="right">
            <el-input v-model.trim="resourceForm.resourcePageName" placeholder="前端页面定义的名称" maxlength="32" />
          </el-tooltip>
        </el-form-item>
        <el-form-item :label="$t('resourceTable.resourceCache')" prop="resourceCache">
          <el-radio-group v-model="resourceForm.resourceCache">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('resourceTable.resourceShow')" prop="resourceShow">
          <el-radio-group v-model="resourceForm.resourceShow">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('resourceTable.description')">
          <el-input v-model.trim="resourceForm.description" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="请输入备注信息" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">{{ $t('table.confirm') }}</el-button>
        <el-button v-else type="primary" @click="updateData">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/**
  Auth: Jeebase
  Created: 2018/1/19-14:54
*/
import { fetchResourceList, createResource, deleteResource, updateResource, checkResourceKey } from '@/api/system/resource'
import waves from '@/directive/waves' // 水波纹指令

export default {
  name: 'ResourceTable',
  directives: {
    waves
  },
  components: { },
  filters: {
    typeNameFilter(type) {
      const typeNameMap = {
        1: '模块',
        2: '菜单',
        4: '接口',
        3: '按钮'
      }
      return typeNameMap[type]
    }
  },
  data() {
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
      expandAll: false,
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
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchResourceList(this.treeQuery).then(response => {
        this.list = response.data
        this.baseList = JSON.parse(JSON.stringify(response.data)) // 数组深复制
        this.listLoading = false
      })
    },
    resetResourceForm() {
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
    handleCreate(row) {
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
        this.$refs['resourceForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['resourceForm'].validate(valid => {
        if (valid) {
          createResource(this.resourceForm).then(response => {
            this.dialogFormVisible = false
            this.resourceForm.id = response.data.id
            if (this.rootFlag) {
              this.resourceForm['children'] = []
              this.list.push(this.resourceForm)
              this.baseList.push(JSON.parse(JSON.stringify(this.resourceForm)))
            } else {
              this.createDataCallBack(this.list)
              this.createDataCallBack(this.baseList)
            }
            this.$message({
              message: '创建成功',
              type: 'success'
            })
          })
        }
      })
    },
    createDataCallBack(dataList) {
      for (const v of dataList) {
        if (v.id === this.resourceForm.parentId) {
          if (!v.children) {
            v['children'] = []
          }
          this.resourceForm['children'] = []
          v.children.push(JSON.parse(JSON.stringify(this.resourceForm)))
          break
        }
        if (v.children && v.children.length > 0) {
          this.createDataCallBack(v.children)
        }
      }
    },
    handleUpdate(row) {
      this.resourceForm = Object.assign({}, row) // copy obj
      // JSON不接受循环对象——引用它们自己的对象
      delete this.resourceForm.parent
      delete this.resourceForm.children
      this.resourceForm.resourceType = parseInt(this.resourceForm.resourceType)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['resourceForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['resourceForm'].validate(valid => {
        if (valid) {
          updateResource(this.resourceForm).then(() => {
            this.dialogFormVisible = false
            this.$message({
              message: '更新成功',
              type: 'success'
            })
            this.updateDataCallBack(this.list)
            this.updateDataCallBack(this.baseList)
          })
        }
      })
    },
    updateDataCallBack(dataList) {
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
    handleDelete(row) {
      this.$confirm(
        '此操作将永久删除该资源：' + row.resourceName + ', 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          this.listLoading = true
          deleteResource(row.id).then(() => {
            this.listLoading = false
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.deleteDataCallBack(row.id, this.list)
            this.deleteDataCallBack(row.id, this.baseList)
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    deleteDataCallBack(id, dataList) {
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
    searchTreeData() {
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
    queryData(dataList) {
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
