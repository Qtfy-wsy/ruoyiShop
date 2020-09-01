<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="商品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入商品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>


      <el-form-item label="审核状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择审核状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="虚拟商品" prop="isVirtual">
        <el-select v-model="queryParams.isVirtual" placeholder="请选择是否是虚拟商品" clearable size="small">
          <el-option
            v-for="dict in isVirtualOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['goods:goods:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['goods:goods:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['goods:goods:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
         <el-dropdown class="filter-item" style="margin-left: 5px;">
                <el-button size="medium" type="primary">批量上下架<i class="el-icon-arrow-down el-icon--right"></i></el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item @click.native="tobatchup()">批量上架</el-dropdown-item>
                  <el-dropdown-item @click.native="tobatchdown()">批量下架</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="goodsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="商品名称" align="center" prop="name" />


       <el-table-column label="商品图片" width="80">
        <template slot-scope="scope"><a :href="scope.row.visitUrl" target="_blank">
        <img :src="scope.row.url" width="50"  height="50"></a></template>
      </el-table-column>
       <el-table-column label="销售价">
              <template slot-scope="scope">{{ scope.row.price.toFixed(2) }}</template>
            </el-table-column>
            <el-table-column label="分类">
              <template slot-scope="scope">{{ scope.row.thirdCategory.name }}</template>
            </el-table-column>
            <el-table-column label="品牌">
              <template slot-scope="scope">{{ scope.row.brand.name }}</template>
            </el-table-column>
      <el-table-column label="佣金比例" align="center" prop="commissionRate" />
      <el-table-column label="二级佣金比例" align="center" prop="sCommissionRate" />
      <el-table-column label="审核状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="上架状态" align="center" prop="shelvesStatus" :formatter="shelvesStatusFormat" />
      <el-table-column label="虚拟商品" align="center" prop="isVirtual" :formatter="isVirtualFormat" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['goods:goods:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['goods:goods:remove']"
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

    <!-- 添加或修改商品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="商品副标题" prop="subtitle">
          <el-input v-model="form.subtitle" placeholder="请输入商品副标题" />
        </el-form-item>
        <el-form-item label="销售价" prop="price">
          <el-input v-model="form.price" placeholder="请输入销售价" />
        </el-form-item>
        <el-form-item label="PC版详情" prop="pcDesc">
          <el-input v-model="form.pcDesc" placeholder="请输入PC版详情" />
        </el-form-item>
        <el-form-item label="手机版详情" prop="mobileDesc">
          <el-input v-model="form.mobileDesc" placeholder="请输入手机版详情" />
        </el-form-item>
        <el-form-item label="seo标题" prop="seoTitle">
          <el-input v-model="form.seoTitle" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="seo关键字" prop="seoKeywords">
          <el-input v-model="form.seoKeywords" placeholder="请输入seo关键字" />
        </el-form-item>
        <el-form-item label="see描述" prop="seoDesc">
          <el-input v-model="form.seoDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="商品的店铺id 如果是平台的则为0 " prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入商品的店铺id 如果是平台的则为0 " />
        </el-form-item>
        <el-form-item label="一级分类id" prop="firstCateId">
          <el-input v-model="form.firstCateId" placeholder="请输入一级分类id" />
        </el-form-item>
        <el-form-item label="二级分类id" prop="secondCateId">
          <el-input v-model="form.secondCateId" placeholder="请输入二级分类id" />
        </el-form-item>
        <el-form-item label="三级分类id" prop="thirdCateId">
          <el-input v-model="form.thirdCateId" placeholder="请输入三级分类id" />
        </el-form-item>
        <el-form-item label="类型id" prop="typeId">
          <el-input v-model="form.typeId" placeholder="请输入类型id" />
        </el-form-item>
        <el-form-item label="品牌id" prop="brandId">
          <el-input v-model="form.brandId" placeholder="请输入品牌id" />
        </el-form-item>
        <el-form-item label="店铺一级分类" prop="storeFcateId">
          <el-input v-model="form.storeFcateId" placeholder="请输入店铺一级分类" />
        </el-form-item>
        <el-form-item label="店铺二级分类" prop="storeScateId">
          <el-input v-model="form.storeScateId" placeholder="请输入店铺二级分类" />
        </el-form-item>
        <el-form-item label="店铺三级分类" prop="storeTcateId">
          <el-input v-model="form.storeTcateId" placeholder="请输入店铺三级分类" />
        </el-form-item>
        <el-form-item label="商品图片" prop="url">
          <el-input v-model="form.url" placeholder="请输入商品图片" />
        </el-form-item>
        <el-form-item label="删除标记   0未删除 1删除 默认0" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标记   0未删除 1删除 默认0" />
        </el-form-item>
        <el-form-item label="创建人的名称" prop="createName">
          <el-input v-model="form.createName" placeholder="请输入创建人的名称" />
        </el-form-item>
        <el-form-item label="修改人的名称" prop="modifyName">
          <el-input v-model="form.modifyName" placeholder="请输入修改人的名称" />
        </el-form-item>
        <el-form-item label="删除人的名字" prop="delName">
          <el-input v-model="form.delName" placeholder="请输入删除人的名字" />
        </el-form-item>
        <el-form-item label="修改时间" prop="modifyTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.modifyTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择修改时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="删除时间" prop="delTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.delTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择删除时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="佣金比例" prop="commissionRate">
          <el-input v-model="form.commissionRate" placeholder="请输入佣金比例" />
        </el-form-item>
        <el-form-item label="二级佣金比例" prop="sCommissionRate">
          <el-input v-model="form.sCommissionRate" placeholder="请输入二级佣金比例" />
        </el-form-item>
        <el-form-item label="审核状态  0 审核通过 1 审核未通过 2 审核中">
          <el-select v-model="form.status" placeholder="请选择审核状态  0 审核通过 1 审核未通过 2 审核中">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品上架状态 0 下架  1上架 2违规下架 默认0">
          <el-select v-model="form.shelvesStatus" placeholder="请选择商品上架状态 0 下架  1上架 2违规下架 默认0">
            <el-option
              v-for="dict in shelvesStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否是虚拟商品 0 否 1 是默认0">
          <el-select v-model="form.isVirtual" placeholder="请选择是否是虚拟商品 0 否 1 是默认0">
            <el-option
              v-for="dict in isVirtualOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品视频地址" prop="video">
          <el-input v-model="form.video" placeholder="请输入商品视频地址" />
        </el-form-item>
        <el-form-item label="商品视频封面地址" prop="videoPic">
          <el-input v-model="form.videoPic" placeholder="请输入商品视频封面地址" />
        </el-form-item>
        <el-form-item label="物流模版id 对应oms_logistics_template表的id" prop="logisticsTemplateId">
          <el-input v-model="form.logisticsTemplateId" placeholder="请输入物流模版id 对应oms_logistics_template表的id" />
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
import { listGoods, getGoods, delGoods, addGoods, updateGoods,updateShelvesStatus, exportGoods } from "@/api/goods/goods";

export default {
  name: "Goods",
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
      // 总条数
      total: 0,
      // 商品表格数据
      goodsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 审核状态  0 审核通过 1 审核未通过 2 审核中字典
      statusOptions: [],
      // 商品上架状态 0 下架  1上架 2违规下架 默认0字典
      shelvesStatusOptions: [],
      // 是否是虚拟商品 0 否 1 是默认0字典
      isVirtualOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        name: undefined,
        storeFcateId: undefined,
        storeScateId: undefined,
        storeTcateId: undefined,
        commissionRate: undefined,
        sCommissionRate: undefined,
        status: undefined,
        isVirtual: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "商品名称不能为空", trigger: "blur" }
        ],
        storeId: [
          { required: true, message: "商品的店铺id 如果是平台的则为0 不能为空", trigger: "blur" }
        ],
        typeId: [
          { required: true, message: "类型id不能为空", trigger: "blur" }
        ],
        brandId: [
          { required: true, message: "品牌id不能为空", trigger: "blur" }
        ],
        delFlag: [
          { required: true, message: "删除标记   0未删除 1删除 默认0不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("audit_thress").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("goods_publish").then(response => {
      this.shelvesStatusOptions = response.data;
    });
    this.getDicts("ye_no01").then(response => {
      this.isVirtualOptions = response.data;
    });
  },
  methods: {
    /** 查询商品列表 */
    getList() {
      this.loading = true;
      listGoods(this.queryParams).then(response => {
        this.goodsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 审核状态  0 审核通过 1 审核未通过 2 审核中字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 商品上架状态 0 下架  1上架 2违规下架 默认0字典翻译
    shelvesStatusFormat(row, column) {
      return this.selectDictLabel(this.shelvesStatusOptions, row.shelvesStatus);
    },
    // 是否是虚拟商品 0 否 1 是默认0字典翻译
    isVirtualFormat(row, column) {
      return this.selectDictLabel(this.isVirtualOptions, row.isVirtual);
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
        subtitle: undefined,
        price: undefined,
        pcDesc: undefined,
        mobileDesc: undefined,
        seoTitle: undefined,
        seoKeywords: undefined,
        seoDesc: undefined,
        storeId: undefined,
        firstCateId: undefined,
        secondCateId: undefined,
        thirdCateId: undefined,
        typeId: undefined,
        brandId: undefined,
        storeFcateId: undefined,
        storeScateId: undefined,
        storeTcateId: undefined,
        url: undefined,
        delFlag: undefined,
        createName: undefined,
        modifyName: undefined,
        delName: undefined,
        createTime: undefined,
        modifyTime: undefined,
        delTime: undefined,
        commissionRate: undefined,
        sCommissionRate: undefined,
        status: undefined,
        shelvesStatus: undefined,
        isVirtual: undefined,
        video: undefined,
        videoPic: undefined,
        logisticsTemplateId: undefined
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
      this.reset();
      this.$router.push(
                    {path: '/goods/addspu'})
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
       this.$router.push(
                          {path: '/goods/updatespu', query: {id:id}})

    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateGoods(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addGoods(this.form).then(response => {
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
      // 更新上下架状态
          updateShelvesStatus(ids, status) {
            if (this.isProcess) {
              return;
            }
            this.isProcess = true;
            updateShelvesStatus(ids, status).then(() => {
              this.isProcess = false;
              this.selectedIds = [];
              if (status == '0') {
                this.$message({
                  type: 'success',
                  message: '批量下架成功!'
                })
              } else {
                this.$message({
                  type: 'success',
                  message: '批量上架成功!'
                })
              }

                this.getList();
            })
          },
      tobatchup() {
            if (!this.ids || this.ids == 0) {
              this.$message({
                type: 'error',
                message: '请至少选择一条记录!'
              })
              return;
            }
            this.$confirm('确定要上架选中的商品吗？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.updateShelvesStatus(this.ids.toString(), '1');
            })
          },
          tobatchdown() {
            if (!this.ids || this.ids == 0) {
              this.$message({
                type: 'error',
                message: '请至少选择一条记录!'
              })
              return;
            }
            this.$confirm('确定要下架选中的商品吗？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.updateShelvesStatus(this.ids.toString(), '0');
            })
          },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除商品编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delGoods(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有商品数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportGoods(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
