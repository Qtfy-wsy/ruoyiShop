<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-button class="filter-item" style="width: 100%" type="primary" icon="el-icon-plus"
                   @click="toAddFirstCategory">添加一级分类
        </el-button>
        <div style="border: 1px solid #ebebeb; margin-top: 20px; border-radius: 4px">
          <el-table
            ref="fCateorys"
            :data="fCateorys"
            v-loading="fLoading"
            height="554"
            style="border-radius: 4px"
            fit
            highlight-current-row
            @cell-click="fclick"
          >
            <el-table-column label="分类名称">
              <template slot-scope="scope">{{ scope.row.name }}</template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button type="primary" icon="el-icon-edit" size="mini" circle
                           @click="toUpdateFCate(scope.row.id)"></el-button>
                <el-button type="danger" icon="el-icon-delete" size="mini" circle
                           @click="deleteDialog(scope.row.id,1)"></el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :span="8">
        <el-button class="filter-item" style="width: 100%" type="primary" icon="el-icon-plus"
                   @click="toAddSecondCategory">添加二级分类
        </el-button>
        <div style="border: 1px solid #ebebeb; margin-top: 20px; border-radius: 4px">
          <el-table
            ref="sCategorys"
            :data="sCategorys"
            v-loading="sLoading"
            height="554"
            style="border-radius: 4px"
            fit
            highlight-current-row
            @cell-click="sclick"
          >
            <el-table-column label="分类名称">
              <template slot-scope="scope">{{ scope.row.name }}</template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button type="primary" icon="el-icon-edit" size="mini" circle
                           @click="toUpdateSCate(scope.row.id)"></el-button>
                <el-button type="danger" icon="el-icon-delete" size="mini" circle
                           @click="deleteDialog(scope.row.id,2)"></el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :span="8">
        <el-button class="filter-item" style="width: 100%" type="primary" icon="el-icon-plus"
                   @click="toAddThirdCategory">添加三级分类
        </el-button>
        <div style="border: 1px solid #ebebeb; margin-top: 20px; border-radius: 4px">
          <el-table
            ref="tCategorys"
            :data="tCategorys"
            v-loading="tLoading"
            height="554"
            style="border-radius: 4px"
            fit
            highlight-current-row
          >
            <el-table-column label="分类名称">
              <template slot-scope="scope">{{ scope.row.name }}</template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button type="primary" icon="el-icon-edit" size="mini" circle
                           @click="toUpdateTCate(scope.row.id)"></el-button>
                <el-button type="danger" icon="el-icon-delete" size="mini" circle
                           @click="deleteDialog(scope.row.id,3)"></el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>

    <el-dialog :title="DiaType==1?'添加一级分类':'修改一级分类'" :visible.sync="fDialog">
      <el-form :model="form" :rules="frules" ref="fform" label-position="right" label-width="100px">
        <el-form-item prop="name">
          <span slot="label">分类名称</span>
          <el-input v-model="form.name" maxlength="10"/>
        </el-form-item>
        <el-form-item>
          <span slot="label"><span class="labelImportant">*</span>分类排序</span>
          <template>
            <el-input-number size="small" v-model="form.sort" controls-position="right" :min="1"
                             :max="99"></el-input-number>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fDialog = false">取消</el-button>
        <el-button type="primary" @click="DiaType==1?addFirstCategory():updateFCate()">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="DiaType==1?'添加二级分类':'修改二级分类'" :visible.sync="sDialog">
      <el-form :model="sform" :rules="srules" ref="sform" label-position="right" label-width="100px">
        <el-form-item prop="name">
          <span slot="label">分类名称</span>
          <el-input v-model="sform.name" maxlength="10"/>
        </el-form-item>
        <el-form-item prop="parentId">
          <span slot="label">上级分类</span>
          <el-select v-model="sform.parentId" style="width: 50%" filterable placeholder="请选择分类">
            <el-option
              v-for="item in fCateorys"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <span slot="label"><span class="labelImportant">*</span>分类排序</span>
          <template>
            <el-input-number size="small" v-model="sform.sort" controls-position="right" :min="1"
                             :max="99"></el-input-number>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="sDialog = false">取消</el-button>
        <el-button type="primary" @click="DiaType==1?addSecondCategory():updateSCate()">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="DiaType==1?'添加三级分类':'修改三级分类'" :visible.sync="tDialog">
      <el-form :model="tform" :rules="trules" ref="tform" label-position="right" label-width="100px">
        <el-form-item prop="name">
          <span slot="label">分类名称</span>
          <el-input v-model="tform.name" maxlength="10"/>
        </el-form-item>
        <el-form-item prop="parentId">
          <span slot="label">上级分类</span>
          <el-select v-model="tform.parentId" style="width: 50%" filterable placeholder="请选择分类">
            <el-option
              v-for="item in sCategorys"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <span slot="label"><span class="labelImportant">*</span>分类排序</span>
          <template>
            <el-input-number size="small" v-model="tform.sort" controls-position="right" :min="1"
                             :max="99"></el-input-number>
          </template>
        </el-form-item>
        <el-form-item prop="rate">
          <span slot="label">分类扣率</span>
          <el-tooltip class="item" effect="dark" content="针对第三方商家进行分类扣率结算时使用，公式：价格 x 扣率" placement="right">
            <el-input maxlength="3" v-model="tform.rate" style="width: 130px">
              <template slot="append">%</template>
            </el-input>
          </el-tooltip>
        </el-form-item>
        <el-form-item prop="specsValue">
          <span slot="label">选择规格</span>
          <el-select @remove-tag="removeTag" v-model="tform.specsValue" style="width: 100%" multiple
                     placeholder="请选择规格">
            <el-option
              v-for="item in specs"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              :disabled="item.disabled"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="typeId">
          <span slot="label">商品属性</span>
          <el-select :disabled="typeDisabled" v-model="tform.typeId" style="width: 50%" filterable placeholder="请选择属性">
            <el-option
              v-for="item in types"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="tDialog = false">取消</el-button>
        <el-button type="primary" @click="DiaType==1?addThirdCategory():updateTCate()">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import {
    queryCategoryByParentId,
    addCategory,
    queryAllTypes,
    queryAllSpecs,
    deleteCategory,
    querCategoryById,
    updateCategory,
    queryThirdCategoryHasSpuById
  } from '@/api/goods/categorys';

  export default {
    data() {
      const rateValidator = (rule, value, callback) => {
        if (!value || !/^100$|^(\d|[1-9]\d)$/.test(value)) {
          callback(new Error('请输入0-100之间的整数'));
        } else {
          callback();
        }
      }
      return {
        typeDisabled: false,// 类型选择框是否可以选择
        DiaType: 1, //  弹窗类型 1 新增 2 修改
        choosedFid: '', // 当前选择的一级分类
        choosedSid: '', // 当前选择的二级分类
        form: {name: '', sort: '', grade: '1'}, // 一级分类
        sform: {name: '', sort: '', grade: '2', parentId: ''}, // 二级分类
        tform: {name: '', sort: '', grade: '3', parentId: '', rate: '', typeId: '', cateSpecs: [], specsValue: []}, // 三级分类
        fCateorys: [], // 一级分类
        sCategorys: [], // 二级分类
        tCategorys: [], // 三级分类
        fDialog: false, // 一级分类的弹出框
        sDialog: false, // 二级分类的弹出框
        tDialog: false, // 三级分类的弹出框
        fLoading: false, // 一级分类的loading
        sLoading: false, // 二级分类的loading
        tLoading: false, // 三级分类的loading
        types: [], // 商品类型
        specs: [], // 商品规格
        frules: {
          name: [
            {
              required: true,
              message: '请输入分类名称',
              trigger: 'blur'
            }
          ]
        },
        srules: {
          name: [
            {
              required: true,
              message: '请输入分类名称',
              trigger: 'blur'
            }
          ],
          parentId: [
            {
              required: true,
              message: '请选择上级分类',
              trigger: 'blur'
            }
          ]
        },
        trules: {
          name: [
            {
              required: true,
              message: '请输入分类名称',
              trigger: 'blur'
            }
          ],
          parentId: [
            {
              required: true,
              message: '请选择上级分类',
              trigger: 'blur'
            }
          ],
          specsValue: [
            {
              required: true,
              message: '请选择规格',
              trigger: 'blur'
            }
          ],
          typeId: [
            {
              required: true,
              message: '请选择属性',
              trigger: 'blur'
            }
          ],
          rate: [
            {
              required: true,
              trigger: 'blur',
              validator: rateValidator
            }
          ]
        },
        isProcess: false, //  接口是否处理中
      }
    },
    created() {
      this.getList()
    },
    methods: {
      // 查询
      getList() {
        this.fLoading = true;
        // 查询一级分类
        queryCategoryByParentId(0).then((fCateorys) => {
          this.fCateorys = fCateorys;
          this.fLoading = false;
          // 查询二级分类
          if (fCateorys && fCateorys.length > 0) {
            // 选中一级分类的第一个
            this.$refs.fCateorys.setCurrentRow(fCateorys[0]);
            this.choosedFid = fCateorys[0].id;

            this.sLoading = true;
            queryCategoryByParentId(fCateorys[0].id).then((sCategorys) => {
              this.sLoading = false;
              this.sCategorys = sCategorys;
              // 查询三级分类
              if (sCategorys && sCategorys.length > 0) {
                // 选中二级分类的第一个
                this.$refs.sCategorys.setCurrentRow(sCategorys[0]);
                this.choosedSid = sCategorys[0].id;
                this.tLoading = true;
                queryCategoryByParentId(sCategorys[0].id).then((tCategorys) => {
                  this.tCategorys = tCategorys;
                  this.tLoading = false;
                  if (tCategorys && tCategorys.length > 0) {
                    // 选中三级级分类的第一个
                    this.$refs.tCategorys.setCurrentRow(tCategorys[0]);
                  }
                })
              }
            })
          }
        })
      },
      // 一级分类点击事件
      fclick(row, column, cell) {
        // 点击的不是第一个单元格直接返回
        if (cell.cellIndex != 0) {
          return;
        }

        this.choosedFid = row.id;

        this.sCategorys = [];
        this.tCategorys = [];

        // 查出二级分类
        this.sLoading = true;
        queryCategoryByParentId(row.id).then((sCategorys) => {
          this.sLoading = false;
          this.sCategorys = sCategorys;
          // 查询三级分类
          if (sCategorys && sCategorys.length > 0) {
            // 选中二级分类的第一个
            this.$refs.sCategorys.setCurrentRow(sCategorys[0]);
            this.choosedSid = sCategorys[0].id;
            this.tLoading = true;
            queryCategoryByParentId(sCategorys[0].id).then((tCategorys) => {
              this.tCategorys = tCategorys;
              this.tLoading = false;
              if (tCategorys && tCategorys.length > 0) {
                // 选中三级级分类的第一个
                this.$refs.tCategorys.setCurrentRow(tCategorys[0]);
              }
            })
          }
        });
      },
      // 二级分类点击事件
      sclick(row, column, cell) {
        // 点击的不是第一个单元格直接返回
        if (cell.cellIndex != 0) {
          return;
        }

        this.choosedSid = row.id;
        this.tCategorys = [];

        // 查询三级分类
        this.tLoading = true;
        queryCategoryByParentId(row.id).then((tCategorys) => {
          this.tCategorys = tCategorys;
          this.tLoading = false;
          if (tCategorys && tCategorys.length > 0) {
            // 选中三级级分类的第一个
            this.$refs.tCategorys.setCurrentRow(tCategorys[0]);
          }
        })
      },
      // 准备添加一级分类
      toAddFirstCategory() {
        this.fDialog = true;
        this.DiaType = 1;
        this.form = {name: '', sort: '', grade: '1'};
        if (this.$refs["fform"]) {
          this.$refs["fform"].resetFields();
        }
      },
      // 准备添加二级分类
      toAddSecondCategory() {
        this.sDialog = true;
        this.DiaType = 1;
        this.sform = {name: '', sort: '', grade: '2', parentId: this.choosedFid};
        if (this.$refs["sform"]) {
          this.$refs["sform"].resetFields();
        }
      },
      // 添加一级分类
      addFirstCategory() {
        this.$refs['fform'].validate(valid => {
          if (!this.isProcess && valid) {
            this.isProcess = true;
            addCategory(this.form).then(() => {
              this.isProcess = false;
              this.fDialog = false;
              this.getList();
              this.$message({
                type: 'success',
                message: '创建成功!'
              });
            })
          }
        });
      },
      // 添加二级分类
      addSecondCategory() {
        this.$refs['sform'].validate(valid => {
          if (!this.isProcess && valid) {
            this.isProcess = true;
            addCategory(this.sform).then(() => {
              this.isProcess = false;
              this.sDialog = false;
              this.fclick({id: this.choosedFid}, '', {cellIndex: 0});
              this.$message({
                type: 'success',
                message: '创建成功!'
              });
            })
          }
        });
      },
      // 准备添加三级级分类
      toAddThirdCategory() {
        this.typeDisabled = false;
        this.tDialog = true;
        this.DiaType = 1;
        this.tform = {
          name: '',
          sort: '',
          grade: '3',
          parentId: this.sCategorys.length > 0 ? this.choosedSid : '',
          rate: '',
          typeId: '',
          cateSpecs: [],
          specsValue: []
        };
        if (this.$refs["tform"]) {
          this.$refs["tform"].resetFields();
        }
        queryAllTypes().then((types) => {
          this.types = types;
        });

        queryAllSpecs().then((specs) => {
          this.specs = specs;
        });
      },
      // 添加三级分类
      addThirdCategory() {
        this.$refs['tform'].validate(valid => {
          if (!this.isProcess && valid) {
            this.isProcess = true;
            const specValues = new Array();
            this.tform.specsValue.forEach(id => specValues.push({specId: id}));
            this.tform.cateSpecs = specValues;
            this.tform.rate = parseFloat(this.tform.rate) / 100;
            addCategory(this.tform).then(() => {
              this.isProcess = false;
              this.tDialog = false;
              this.sclick({id: this.choosedSid}, '', {cellIndex: 0});
              this.$message({
                type: 'success',
                message: '创建成功!'
              });
            })
          }
        });
      },
      // 弹出删除分类框
      deleteDialog(id, index) {
        this.$confirm('是否确认删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.delCategory(id, index);
        })
      },
      // 删除分类
      delCategory(id, index) {
        if (this.isProcess) {
          return;
        }
        this.isProcess = true;
        deleteCategory(id).then((res) => {
          this.isProcess = false;
          if (res == -1) {
            this.$message({
              type: 'error',
              message: '该分类下包含子分类，不能删除!'
            });
          } else if (res == -2) {
            this.$message({
              type: 'error',
              message: '该分类下有商品，不能删除!'
            });
          } else {
            // 删除一级分类
            if (index == 1) {
              this.getList();
            } else if (index == 2) {
              //删除二级分类 重新刷新二级分类
              this.fclick({id: this.choosedFid}, '', {cellIndex: 0});
            } else if (index == 3) {
              //删除三级分类 重新刷新三级分类
              this.sclick({id: this.choosedSid}, '', {cellIndex: 0});
            }
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }
        });
      },
      //准备更新一级分类
      toUpdateFCate(id) {
        querCategoryById(id).then((category) => {
          this.DiaType = 2;
          this.fDialog = true;
          this.form = {id: category.id, name: category.name, sort: category.sort, grade: category.grade};
          if (this.$refs["fform"]) {
            this.$refs["fform"].resetFields();
          }
        });
      },
      // 更新一级分类
      updateFCate() {
        this.$refs['fform'].validate(valid => {
          if (!this.isProcess && valid) {
            this.isProcess = true;
            updateCategory(this.form).then(() => {
              this.isProcess = false;
              this.fDialog = false;
              this.getList();
              this.$message({
                type: 'success',
                message: '更新成功!'
              });
            })
          }
        });
      },
      //准备更新二级分类
      toUpdateSCate(id) {
        querCategoryById(id).then((category) => {
          this.DiaType = 2;
          this.sDialog = true;
          this.sform = {
            id: category.id,
            name: category.name,
            sort: category.sort,
            grade: category.grade,
            parentId: category.parentId
          };
          if (this.$refs["sform"]) {
            this.$refs["sform"].resetFields();
          }
        });
      },
      // 修改二级分类
      updateSCate() {
        this.$refs['sform'].validate(valid => {
          if (!this.isProcess && valid) {
            this.isProcess = true;
            updateCategory(this.sform).then(() => {
              this.isProcess = false;
              this.sDialog = false;
              this.fclick({id: this.choosedFid}, '', {cellIndex: 0});
              this.$message({
                type: 'success',
                message: '更新成功!'
              });
            })
          }
        });
      },
      //准备更新三级分类
      toUpdateTCate(id) {

        this.typeDisabled = false;

        // 查询所有类型
        queryAllTypes().then((types) => {
          this.types = types;
        });

        // 查询所有规格
        queryAllSpecs().then((specs) => {
          this.specs = specs;
        });

        // 查询三级分类
        querCategoryById(id).then((category) => {

          this.DiaType = 2;
          this.tform = {
            id: category.id,
            name: category.name,
            sort: category.sort,
            grade: '3',
            parentId: category.parentId,
            rate: Math.round(category.rate * 100),
            typeId: category.typeId,
            cateSpecs: category.cateSpecs,
            specsValue: category.cateSpecs.map(spec => spec.specId)
          }
          if (this.$refs["tform"]) {
            this.$refs["tform"].resetFields();
          }

          // 查询三级分类是否关联了商品
          queryThirdCategoryHasSpuById(id).then((res) => {
            // 三级分类关联了商品
            if (res == -1) {
              this.typeDisabled = true;
              this.specs.forEach((spec) => {
                // 分类下关联的规格
                if (this.tform.cateSpecs.filter(item => spec.id == item.specId).length > 0) {
                  // 设置分类已关联的规格不能删除
                  spec.disabled = true;
                }

              });
            }

            this.tDialog = true;
          });

        });
      },
      // 删除规格
      removeTag(tag) {
        // 如果修改的三级分类关联了商品，那么三级分类关联的规格就不能删除 只能新增
        if (this.typeDisabled) {
          // 判断删除的规格是否是三级分类以前就关联的规格
          if (this.tform.cateSpecs.filter(item => item.specId == tag).length > 0) {
            this.tform.specsValue.push(tag);
            this.tform.specsValue.sort();
          }
        }
      },
      // 更新三级分类
      updateTCate() {
        this.$refs['tform'].validate(valid => {
          if (!this.isProcess && valid) {
            this.isProcess = true;
            const specValues = new Array();
            this.tform.specsValue.forEach(id => specValues.push({specId: id}));
            this.tform.cateSpecs = specValues;
            this.tform.rate = parseFloat(this.tform.rate) / 100;
            updateCategory(this.tform).then(() => {
              this.isProcess = false;
              this.tDialog = false;
              this.sclick({id: this.choosedSid}, '', {cellIndex: 0});
              this.$message({
                type: 'success',
                message: '更新成功!'
              });
            })
          }
        });
      },
    },
  }
</script>
