<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 基础表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-select v-model="selectClass" placeholder="请选择科目" class="handle-select mr20">
                    <el-option label="语文" value="语文"></el-option>
                    <el-option label="数学" value="数学"></el-option>
                </el-select>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <span style="margin-left: 1100px;">
                    <el-button type="primary" icon="el-icon-search" @click="">添加试题</el-button>
                </span>
            </div>
            <el-table :data=" ProblemSubject" border class="table" ref="multipleTable"
                header-cell-class-name="table-header">
                <el-table-column prop="pid" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="title" label="题目类型"></el-table-column>
                <el-table-column label="学科">
                    <template #default="scope">{{ scope.row.subjectName }}</template>
                </el-table-column>
                <el-table-column prop="description" label="题目内容"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>


import request from "../../utils/request";

export default {
    name: "basetable",
    data() {
        return {
          ProblemSubject:[],
        }
    },
  created() {
    request.get("/problemsubject/all", {
      params: {

      }
    }).then(res => {
      console.log(res.data)
      this.ProblemSubject = res.data

    })
  }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 200px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}

.table {
    width: 100%;
    font-size: 14px;
}

.red {
    color: #ff0000;
}

.mr20 {
    margin-right: 20px;
}

.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>