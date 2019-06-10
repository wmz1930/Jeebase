<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form label-width="80px">
        <el-row>
          <el-col :span="4">
            <el-input v-model="listQuery.methodName" placeholder="接口名称" style="width: 150px;" class="filter-item" maxlength="32" @keyup.enter.native="handleFilter" />
          </el-col>
          <el-col :span="4">
            <el-input v-model="listQuery.creator" placeholder="操作人" style="width: 150px;" class="filter-item" maxlength="32" @keyup.enter.native="handleFilter" />
          </el-col>
          <el-col :span="4">
            <el-select v-model="listQuery.logType" placeholder="日志类型" clearable style="width: 150px" class="filter-item">
              <el-option v-for="item in typeOption" :key="item.key" :label="item.label" :value="item.key" />
            </el-select>
          </el-col>
          <el-col :span="6">
            <el-date-picker
              v-model="listQuery.dateRange"
              :picker-options="pickerOptions"
              type="datetimerange"
              align="right"
              class="filter-date-item"
              value-format="yyyy-MM-dd HH:mm:ss"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            />
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item style="float:right;">
              <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
              <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">{{ $t('table.export') }}</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

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
      <el-table-column label="接口名称" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.methodName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入参" width="" align="center">
        <template slot-scope="scope">
          <span class="link-type params" @click="handleInShow(scope.row.inParams)">{{ scope.row.inParams }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出参" width="" align="center">
        <template slot-scope="scope">
          <span class="link-type params" @click="handleOutShow(scope.row.outParams)">{{ scope.row.outParams }}</span>
        </template>
      </el-table-column>
      <el-table-column label="日志类型" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.logType | logTypeFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作名称" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.operationName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作的IP" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.operationIp }}</span>
        </template>
      </el-table-column>
      <el-table-column label="记录时间" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作人" width="" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.creator }}</span>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="getList" />

    <el-dialog :visible.sync="dialogFormVisible" :title="textMap[dialogStatus]">
      <div class="editor-container">
        <json-editor ref="jsonEditor" v-model="jsonValue" />
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchList } from '@/api/system/common/log'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import JsonEditor from '@/components/JsonEditor'

export default {
  name: 'LogTable',
  components: { Pagination, JsonEditor },
  directives: {
    waves
  },
  filters: {
    logTypeFilter(logType) {
      const logTypeMap = {
        1: '操作日志'
      }
      return logTypeMap[logType]
    }
  },
  data() {
    return {
      tableKey: 0,
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        current: 1,
        size: 20,
        methodName: '',
        logType: '',
        operationName: '',
        dateRange: [],
        startTime: '',
        endTime: ''
      },
      typeOption: [
        { label: '操作日志', key: '1' }
      ],
      dialogFormVisible: false,
      textMap: {
        outParams: '出参详情',
        inParams: '入参详情'
      },
      dialogStatus: '',
      downloadLoading: false,
      jsonValue: '',
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      if (this.listQuery.dateRange && this.listQuery.dateRange.length === 2) {
        this.listQuery.startTime = this.listQuery.dateRange[0]
        this.listQuery.endTime = this.listQuery.dateRange[1]
      }
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
    handleInShow(rowJson) {
      this.jsonValue = ''
      this.dialogStatus = 'inParams'
      this.jsonValue = JSON.parse(rowJson)
      this.dialogFormVisible = true
    },
    handleOutShow(rowJson) {
      this.jsonValue = ''
      this.dialogStatus = 'outParams'
      this.jsonValue = JSON.parse(rowJson)
      this.dialogFormVisible = true
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = [
          '序号',
          '接口名称',
          '入参',
          '出参',
          '日志类型',
          '操作名称',
          '操作ID',
          '记录时间',
          '操作人'
        ]
        const filterVal = [
          'id',
          'methodName',
          'inParams',
          'outParams',
          'logType',
          'operationName',
          'operationIp',
          'createTime',
          'creator'
        ]
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '操作日志列表'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          if (j === 'createTime') {
            return parseTime(v[j])
          } else if (j === 'logType') {
            return this.$options.filters['logTypeFilter'](v[j])
          } else {
            return v[j]
          }
        })
      )
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
.params{
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
</style>
