<template>
  <div>
    <div class="container">
      <!--      班级选择器-->
      <div class="handle-box">
        <el-select v-model="select_class" placeholder="请选择班级" class="handle-select mr10">
          <el-option label="所有班级" value=""></el-option>
          <el-option v-for="classes in Classes" :label="classes.className" :value="classes.className"/>
        </el-select>
        <el-input v-model="search_real_name" placeholder="请输入学生真实姓名" class="handle-input mr10" clearable></el-input>
        <el-button type="primary" icon="el-icon-search" @click="load">搜索</el-button>
      </div>
<!--      表格-->
      <el-table   :data="UserData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="uid" label="ID" width="65" align="center" sortable></el-table-column>
        <el-table-column prop="username" label="用户名" width="120" align="center"></el-table-column>
        <el-table-column prop="realName" label="真实姓名" width="100" align="center"></el-table-column>
        <el-table-column label="班级" width="160" align="center">
          <template #default="scope">
            <p v-if="scope.row.className == '未加入班级'"><el-tag type="danger" style="font-size: 14px;font-weight: bolder">{{ scope.row.className }}</el-tag></p>
            <p v-else><el-tag type="primary" style="font-size: 14px;font-weight: bolder">{{ scope.row.className }}</el-tag></p>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="个人简介"  align="center"></el-table-column>
        <el-table-column prop="email" label="邮箱" align="center" width="200">
          <template #default="scope">
            <u style="font-size: 15px; font-weight: bolder;color: #7896bb;">{{scope.row.email}}</u>
          </template>
        </el-table-column>
        <el-table-column label="类型" align="center" width="150">
          <template #default="scope">
            <div v-if="scope.row.role == 1" style="font-weight: bolder">
              <el-tag size="large" style="font-size: 14px" effect="dark">学生</el-tag>
            </div>
            <div v-else>
              <el-tag size="large" style="font-size: 14px" type="warning" effect="dark">老师</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="注册时间" align="center" width="200">
          <template #default="scope">
            <p style="font-family: -webkit-pictograph,cursive;font-size: 17px">{{scope.row.createTime}}</p>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center" fixed="right">
          <template #default="scope">
            <el-button type="text" icon="el-icon-lx-attention" @click="handleEdit(scope.row)">查看
            </el-button>
            <el-popconfirm title="你确定要删除这条记录吗?" @confirm="deleteExamData(scope.row.id)">
              <template #reference>
                <el-button type="text" icon="el-icon-delete" class="red">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--  分页-->
    <div style="margin-top: 10px;">
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     pager-count="10"
                     background
                     layout="total, sizes, prev, pager, next, jumper"
                     :page-sizes="[5, 10, 20]"
                     :total="total"
                     :page-size="pageSize"
                     :current-page="currentPage"/>
    </div>
    <!--    弹框 -->
    <el-dialog
        v-model="dialogVisible"
        width="60%"
        :before-close="handleClose"
        center
        top="10vh"
    >
    </el-dialog>
  </div>
</template>

<script>
import request from "../../utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  data() {
    return {
      Classes:[],
      dialogVisible: false,
      search_real_name: '',
      select_class: '',
      total: 0,
      pageSize: 10,
      currentPage: 1,
      UserData: [],
    }
  },
  methods: {
    // 加载所有提交数据
    load() {
      request.get("/user/all", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search_real_name: this.search_real_name,
          select_class: this.select_class,
        }
      }).then(res => {
        this.UserData = res.data.records
        this.total = res.data.total
        console.log(res)
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum
      this.load()
    },
    handleEdit(exam_user) {
      this.$router.push({
        path: '/showexam',
        query: {
          eid: exam_user.eid,
          uid: exam_user.uid,
        }
      })
    },
    deleteExamData(id) {
      request.delete("/examdata", {params: {id: id}}).then(res => {
        ElMessage({
          type: "success",
          message: "删除成功"
        })
        this.load()
      })
    },
  },
  created() {
    request.get("/class/all").then(res => {
      this.Classes = res.data
      console.log(res.data)
    })
    this.load()
  },
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 180px;
}

.handle-input {
  width: 200px;
  display: inline-block;
}

.table {
  width: 100%;
  font-size: 14px;
}

.red {
  color: #ff0000;
}

.mr10 {
  margin-right: 10px;
}

.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 40px;
}

/deep/ .el-carousel__item.is-animating {
  background: #f7d1d1;
}

/deep/ .el-dialog--center .el-dialog__body {
  text-align: initial;
  padding: 0 10px 0 10px;
  background: #baddde;
}

/deep/ .el-card {
  border: 1px solid #0c0c0c;
}

/deep/ .el-card__header {
  background-color: #7497b4;
  padding: 1px
}

/deep/ .el-carousel__indicator--horizontal button {
  width: 8px;
  height: 8px;
  background: #ffffff;
  border-radius: 50%;
  opacity: 0.5;
}

/deep/ .el-carousel__indicator--horizontal.is-active button {
  width: 24px;
  height: 8px;
  background: #ffffff;
  opacity: 0.5;
  border-radius: 10px;
}

</style>
