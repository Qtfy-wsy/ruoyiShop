<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="类型名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入类型名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['goods:type:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['goods:type:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['goods:type:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['goods:type:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="类型名称" align="center" prop="name" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['goods:type:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['goods:type:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改商品类型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="类型名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入类型名称" />
        </el-form-item>
        <el-form-item>
                 <span slot="label"><span class="labelImportant">*</span>属性值</span>
                 <el-button class="filter-item" size="medium" type="primary" icon="el-icon-plus" @click="addAttribute">
                   添加属性
                 </el-button>
               </el-form-item>

               <el-form-item>
                 <el-table
                   :data="form.attributes"
                   border
                   fit
                   style="line-height: 20px"
                 >
                   <el-table-column label="属性名" width="150">
                     <template slot-scope="scope">
                       <el-input v-model="scope.row.name" size="medium" class="filter-item"/>
                     </template>
                   </el-table-column>
                   <el-table-column label="属性值">
                     <template slot-scope="scope">
                       <el-tag
                         :key="tag.index"
                         v-for="tag in scope.row.attributeValues"
                         :closable="!isLinkedSpu || (isLinkedSpu && !tag.id)"
                         :disable-transitions="false"
                         @close="deleteAttrValue(tag,scope.row)">
                         {{tag.value}}
                       </el-tag>
                       <el-input
                         class="input-new-tag"
                         v-if="scope.row.addAttrValueShow"
                         v-model="scope.row.inputAttrValue"
                         ref="saveTagInput"
                         size="small"
                         @keyup.enter.native="addAttrValue(scope.row)"
                         @blur="addAttrValue(scope.row)"
                       >
                       </el-input>
                       <el-button v-else class="button-new-tag" size="small" @click="toAddAttrValue(scope.row)">+ 添加属性值
                       </el-button>
                     </template>
                   </el-table-column>
                   <el-table-column label="排序" width="100">
                     <template slot-scope="scope">
                       <el-input-number style="width: 80px" v-model="scope.row.sort" size="small" controls-position="right"
                                        :min="1" :max="99"></el-input-number>
                     </template>
                   </el-table-column>
                   <el-table-column label="操作" width="100" align="center" class-name="small-padding fixed-width">
                     <!-- 类型没有商品使用的情况下只要不是第一个都可以删除，如果类型被商品使用的情况下则新增的属性可以删除（id为空就表示新增的）-->
                     <template slot-scope="scope" v-if="(scope.$index !=0 && !isLinkedSpu) || (isLinkedSpu && !scope.row.id)">
                       <el-button type="text" icon="el-icon-delete" size="mini" @click.native.prevent="deleteAttribute(scope.$index)">删除
                       </el-button>
                     </template>
                   </el-table-column>
                 </el-table>
               </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listType, getType, delType, addType, updateType, exportType,checkTypeAssociated } from "@/api/goods/type";

export default {
  name: "Type",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
       isLinkedSpu: false, // 是否关联商品判断
      // 总条数
      total: 0,
      // 商品类型表格数据
      typeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        name: undefined,
      },
      // 表单参数
             form: {name: '', attributes: [{name: '', attributeValues: [], sort: '', addAttrValueShow: false}], id: ''}, // 类型表单

      // 表单校验
      rules: {
        delFlag: [
          { required: true, message: "删除标记 0 未删除 1删除 默认0不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商品类型列表 */
    getList() {
      this.loading = true;
      listType(this.queryParams).then(response => {
        this.typeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        name: undefined,
        delFlag: undefined,
        createName: undefined,
        modifyName: undefined,
        delName: undefined,
        createTime: undefined,
        modifyTime: undefined,
        delTime: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 0;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
   //   this.reset();
      this.open = true;
      this.title = "添加商品类型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
       checkTypeAssociated(id).then(res => {
                this.isLinkedSpu = res != 0;
                console.log(this.isLinkedSpu)
                getType(id).then(res => {
                  this.resetForm();
                  res.data.attributes.forEach(attr => {
                    attr.addAttrValueShow = false;
                    attr.attributeValues.forEach(attrValue => attrValue.index = attrValue.id);
                  });
                  this.form = res.data;
                 this.open = true;
                         this.title = "修改商品类型";
                })
              })


    },
     // 新增属性
          addAttribute() {
            this.form.attributes.push({name: '', attributeValues: [], sort: '', addAttrValueShow: false})
          },
          // 删除属性
          deleteAttribute(index) {
            this.form.attributes.splice(index, 1)
          },
           // 校验商品类型
                validateType() {
                  let validateResult = true;
                  let errorMsg = '';
                  for (var i = 0; i < this.form.attributes.length; i++) {
                    if (!this.form.attributes[i].name) {
                      validateResult = false;
                      errorMsg = '请填写属性名';
                      break;
                    }

                    //  没有属性值
                    if (this.form.attributes[i].attributeValues.length == 0) {
                      validateResult = false;
                      errorMsg = '请填写属性值';
                      break;
                    }
                  }

                  if (!validateResult) {
                    this.$message({
                      message: errorMsg,
                      type: 'error'
                    })
                  }
                  return validateResult;
                },
                // 判断类型是否可以删除，如果类型被三级分类关联 则不能删除
                      isTypeCanDelete(ids) {
                        return queryThirdCategory().then((res) => {
                          // 所有被三级分类关联的类型id
                          let linkedTypeIds = res.map(item => item.typeId);
                          // 被三级分类关联的类型id
                          let sameTypeIds = ids.filter(item => linkedTypeIds.indexOf(item) > -1);
                          return sameTypeIds.length == 0;
                        })
                      },

                 // 删除属性值
                      deleteAttrValue(tag, attribute) {
                        attribute.attributeValues.splice(attribute.attributeValues.findIndex(item => item.index === tag.index), 1)
                      },
                      // 准备添加属性值
                      toAddAttrValue(attribute) {
                        attribute.addAttrValueShow = true
                        this.$nextTick(_ => {
                          this.$refs.saveTagInput.$refs.input.focus()
                        })
                      },
                      // 添加属性值
                      addAttrValue(attribute) {
                        let inputValue = attribute.inputAttrValue
                        if (inputValue) {
                          attribute.attributeValues.push({value: inputValue, index: Date.parse(new Date())})
                        }
                        attribute.addAttrValueShow = false;
                        attribute.inputAttrValue = ''
                      },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid && this.validateType()) {
          if (this.title == "修改商品类型") {
            updateType(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addType(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
       this.isTypeCanDelete([ids]).then(res => {
        if (res) {
      this.$confirm('是否确认删除商品类型编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delType(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
        } else {
        this.msgSuccess("所选属性含有关联三级分类不可删除!");

                   }
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有商品类型数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportType(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
