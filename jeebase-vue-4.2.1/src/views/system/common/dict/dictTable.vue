<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model.trim="treeQuery.dictName" :placeholder="$t('dictTable.dictName')" style="width: 150px;" class="filter-item" maxlength="32" />
      <el-input v-model.trim="treeQuery.dictCode" :placeholder="$t('dictTable.dictCode')" style="width: 150px;" class="filter-item" maxlength="11" />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="searchTreeData()">{{ $t('table.search') }}</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate(false)">{{ $t('table.add') }}</el-button>
      <el-alert
        :closable="false"
        style="width: 300px;"
        class="filter-item"
        title="一级列表为字典分类，字典值禁止重复"
        type="warning"
      />
    </div>

    <el-table :data="list" row-key="id" border>
      <el-table-column :label="$t('dictTable.dictName')">
        <template slot-scope="scope">
          <span :id="scope.row.id">{{ scope.row.dictName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('dictTable.dictCode')">
        <template slot-scope="scope">
          <span :id="scope.row.dictCode">{{ scope.row.dictCode }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('dictTable.dictOrder')">
        <template slot-scope="scope">
          <span>{{ scope.row.dictOrder }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('dictTable.dictStatus')">
        <template slot-scope="scope">
          <span>{{ scope.row.dictStatus | statusFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('dictTable.description')">
        <template slot-scope="scope">
          <span>{{ scope.row.description }}</span>
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
      <el-form ref="dictForm" :model="dictForm" :rules="rules" label-width="100px" class="dictForm" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('dictTable.dictName')" prop="dictName">
          <el-input v-model.trim="dictForm.dictName" placeholder="输入字典名称" maxlength="32" />
        </el-form-item>
        <el-form-item :label="$t('dictTable.dictCode')" prop="dictCode">
          <el-input v-model.trim="dictForm.dictCode" placeholder="输入字典类型/字典值" maxlength="32" />
        </el-form-item>
        <el-form-item :label="$t('dictTable.dictOrder')" prop="dictOrder">
          <el-input v-model.number="dictForm.dictOrder" placeholder="输入字典排序" maxlength="32" />
        </el-form-item>
        <el-form-item :label="$t('dictTable.dictStatus')" prop="dictStatus">
          <el-radio-group v-model="dictForm.dictStatus">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('dictTable.description')">
          <el-input v-model.trim="dictForm.description" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="请输入备注信息" />
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
  Auth: Lei.j1ang
  Created: 2018/1/19-14:54
*/
import { fetchList, createDict, deleteDict, updateDict } from '@/api/system/common/dict'
import waves from '@/directive/waves' // 水波纹指令

export default {
  name: 'DictTable',
  directives: {
    waves
  },
  components: { },
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: '启用',
        0: '禁用'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      expandAll: false,
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
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchList(this.treeQuery).then(response => {
        this.list = response.data
        this.baseList = JSON.parse(JSON.stringify(response.data)) // 数组深复制
        this.listLoading = false
      })
    },
    resetDictForm() {
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
    handleCreate(row) {
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
        this.$refs['dictForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dictForm'].validate(valid => {
        if (valid) {
          createDict(this.dictForm).then(response => {
            this.dialogFormVisible = false
            this.dictForm.id = response.data.id
            if (this.rootFlag) {
              this.dictForm['children'] = []
              this.list.push(this.dictForm)
              this.baseList.push(
                JSON.parse(JSON.stringify(this.dictForm))
              )
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
        if (v.id === this.dictForm.parentId) {
          if (!v.children) {
            v['children'] = []
          }
          this.dictForm['children'] = []
          v.children.push(JSON.parse(JSON.stringify(this.dictForm)))
          break
        }
        if (v.children && v.children.length > 0) {
          this.createDataCallBack(v.children)
        }
      }
    },
    handleUpdate(row) {
      this.dictForm = Object.assign({}, row) // copy obj
      // JSON不接受循环对象——引用它们自己的对象
      delete this.dictForm.parent
      delete this.dictForm.children
      this.dictForm.dictStatus = parseInt(this.dictForm.dictStatus)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dictForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dictForm'].validate(valid => {
        if (valid) {
          updateDict(this.dictForm).then(() => {
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
        if (v.id === this.dictForm.id) {
          Object.assign(v, JSON.parse(JSON.stringify(this.dictForm)))
          break
        }
        if (v.children && v.children.length > 0) {
          this.updateDataCallBack(v.children)
        }
      }
    },
    handleDelete(row) {
      this.$confirm(
        '此操作将永久删除该字典：' + row.dictName + ', 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          this.listLoading = true
          deleteDict(row.id).then(() => {
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
          this.treeQuery.dictName === '' &&
          this.treeQuery.dictCode === ''
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
