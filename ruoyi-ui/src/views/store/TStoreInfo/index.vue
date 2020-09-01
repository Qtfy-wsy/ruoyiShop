<template>
  <div class="app-container">
  <el-radio-group v-model="storeStatus" style="margin-bottom: 20px">
        <el-radio-button :label="0" @click.native="getList(1)">未审核商家列表</el-radio-button>
        <el-radio-button :label="1" @click.native="getList(2)">已审核商家列表</el-radio-button>
        <el-radio-button :label="2" @click.native="getList(3)">审核不通过商家列表</el-radio-button>
      </el-radio-group>
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="店铺名称" prop="storeName">
        <el-input
          v-model="queryParams.storeName"
          placeholder="请输入店铺名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择店铺状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="公司名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入公司名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="店铺类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择店铺类型" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
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
          v-hasPermi="['store:TStoreInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['store:TStoreInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['store:TStoreInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['store:TStoreInfo:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="TStoreInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="店铺名称" align="center" prop="storeName" />
      <el-table-column label="店铺状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="公司名称" align="center" prop="companyName" />

      <el-table-column label="店铺评分" align="center" prop="aveScore" />
      <el-table-column label="店铺有效期" align="center" prop="effectiveTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectiveTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="店铺类型" align="center" prop="type" :formatter="typeFormat" />

      <el-table-column label="营业时间" align="center" prop="businessTime" />

      <el-table-column label="店铺头像" align="center" prop="avatarPicture" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['store:TStoreInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['store:TStoreInfo:remove']"
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

    <!-- 添加或修改店铺信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="店铺名称" prop="storeName">
          <el-input v-model="form.storeName" placeholder="请输入店铺名称" />
        </el-form-item>
        <el-form-item label="店铺状态">
          <el-select v-model="form.status" placeholder="请选择店铺状态">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公司名称" prop="companyName">
          <el-input v-model="form.companyName" placeholder="请输入公司名称" />
        </el-form-item>
        <el-form-item label="公司地址" prop="companyAddress">
          <el-input v-model="form.companyAddress" placeholder="请输入公司地址" />
        </el-form-item>
        <el-form-item label="公司电话" prop="companyPhone">
          <el-input v-model="form.companyPhone" placeholder="请输入公司电话" />
        </el-form-item>
        <el-form-item label="公司邮箱" prop="companyEmail">
          <el-input v-model="form.companyEmail" placeholder="请输入公司邮箱" />
        </el-form-item>
        <el-form-item label="联系人" prop="contactPerson">
          <el-input v-model="form.contactPerson" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系人电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系人电话" />
        </el-form-item>
        <el-form-item label="法定代表人" prop="legalPerson">
          <el-input v-model="form.legalPerson" placeholder="请输入法定代表人" />
        </el-form-item>
        <el-form-item label="身份证号码" prop="cardNo">
          <el-input v-model="form.cardNo" placeholder="请输入身份证号码" />
        </el-form-item>
        <el-form-item label="身份证照片" prop="cardPic">
          <el-input v-model="form.cardPic" placeholder="请输入身份证照片" />
        </el-form-item>
        <el-form-item label="营业执照号" prop="busLicenec">
          <el-input v-model="form.busLicenec" placeholder="请输入营业执照号" />
        </el-form-item>
        <el-form-item label="营业执照图片" prop="busLicenecPic">
          <el-input v-model="form.busLicenecPic" placeholder="请输入营业执照图片" />
        </el-form-item>
        <el-form-item label="经营范围" prop="businessScope">
          <el-input v-model="form.businessScope" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="组织机构代码图片" prop="orgPic">
          <el-input v-model="form.orgPic" placeholder="请输入组织机构代码图片" />
        </el-form-item>
        <el-form-item label="税务登记图片" prop="taxPic">
          <el-input v-model="form.taxPic" placeholder="请输入税务登记图片" />
        </el-form-item>
        <el-form-item label="银行开户名" prop="bankUserName">
          <el-input v-model="form.bankUserName" placeholder="请输入银行开户名" />
        </el-form-item>
        <el-form-item label="公司银行账号" prop="bankAccount">
          <el-input v-model="form.bankAccount" placeholder="请输入公司银行账号" />
        </el-form-item>
        <el-form-item label="开户银行支行名称" prop="bankName">
          <el-input v-model="form.bankName" placeholder="请输入开户银行支行名称" />
        </el-form-item>
        <el-form-item label="支行银行号" prop="bankNumber">
          <el-input v-model="form.bankNumber" placeholder="请输入支行银行号" />
        </el-form-item>
        <el-form-item label="开户银行所在地" prop="bankAddress">
          <el-input v-model="form.bankAddress" placeholder="请输入开户银行所在地" />
        </el-form-item>
        <el-form-item label="开户银行许可证电子版" prop="bankPic">
          <el-input v-model="form.bankPic" placeholder="请输入开户银行许可证电子版" />
        </el-form-item>
        <el-form-item label="结算周期" prop="billingCycle">
          <el-input v-model="form.billingCycle" placeholder="请输入结算周期" />
        </el-form-item>
        <el-form-item label="0三证合一 1 三证不合一" prop="ismerge">
          <el-input v-model="form.ismerge" placeholder="请输入0三证合一 1 三证不合一" />
        </el-form-item>
        <el-form-item label="删除标记 0 未删除 1 删除 默认0 " prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标记 0 未删除 1 删除 默认0 " />
        </el-form-item>
        <el-form-item label="客服QQ" prop="serviceQq">
          <el-input v-model="form.serviceQq" placeholder="请输入客服QQ" />
        </el-form-item>
        <el-form-item label="拒绝原因" prop="reason">
          <el-input v-model="form.reason" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="店铺评分" prop="aveScore">
          <el-input v-model="form.aveScore" placeholder="请输入店铺评分" />
        </el-form-item>
        <el-form-item label="店铺有效期" prop="effectiveTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.effectiveTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择店铺有效期">
          </el-date-picker>
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
        <el-form-item label="店铺类型 0 普通店铺 1 加盟 2连锁">
          <el-select v-model="form.type" placeholder="请选择店铺类型 0 普通店铺 1 加盟 2连锁">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="经度" prop="longitude">
          <el-input v-model="form.longitude" placeholder="请输入经度" />
        </el-form-item>
        <el-form-item label="纬度" prop="latitude">
          <el-input v-model="form.latitude" placeholder="请输入纬度" />
        </el-form-item>
        <el-form-item label="营业时间" prop="businessTime">
          <el-input v-model="form.businessTime" placeholder="请输入营业时间" />
        </el-form-item>
        <el-form-item label="公交线路" prop="busRoutes">
          <el-input v-model="form.busRoutes" placeholder="请输入公交线路" />
        </el-form-item>
        <el-form-item label="店铺头像" prop="avatarPicture">
          <el-input v-model="form.avatarPicture" placeholder="请输入店铺头像" />
        </el-form-item>
        <el-form-item label="省id" prop="provinceId">
          <el-input v-model="form.provinceId" placeholder="请输入省id" />
        </el-form-item>
        <el-form-item label="市id" prop="cityId">
          <el-input v-model="form.cityId" placeholder="请输入市id" />
        </el-form-item>
        <el-form-item label="区域id" prop="districtId">
          <el-input v-model="form.districtId" placeholder="请输入区域id" />
        </el-form-item>
        <el-form-item label="公司详细地址" prop="companyDetailAddress">
          <el-input v-model="form.companyDetailAddress" placeholder="请输入公司详细地址" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="审核商家" :visible.sync="auditDialogVisible">
          <el-form label-position="right" :model="form" :rules="rules" ref="form" label-width="100px">
            <el-form-item label="审核">
              <el-select class="filter-item" v-model="auditStatusValue" size="medium" style="width: 150px">
                <el-option label="通过" value="2"></el-option>
                <el-option label="打回" value="3"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-if="auditStatusValue == 3" prop="reason">
              <span slot="label">拒绝原因</span>
              <el-input type="text" v-model="form.reason"/>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="auditDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="auditData">确定</el-button>
          </div>
        </el-dialog>
  </div>
</template>

<script>
import { listTStoreInfo, getTStoreInfo, delTStoreInfo, addTStoreInfo, updateTStoreInfo, exportTStoreInfo,
 querySellerAduitList,  //分页查询审核商家列表
     deleteStore, //删除商家
     passStoreAudit, //审核通过
     refuseStoreAudit, //审核拒绝
     closeStores } from "@/api/store/TStoreInfo";

export default {
  name: "TStoreInfo",
  data() {
    return {
     tempStoreId: '',//当前操作的店铺 id
            storeStatus: 0,
            auditStatusValue: '',  //审核标记 2通过 3打回
            auditDialogVisible: false, //显示标记
            refusereason: '', //拒绝原因
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
      // 店铺信息表格数据
      TStoreInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 店铺状态

      statusOptions: [],
      // 店铺类型 0 普通店铺 1 加盟 2连锁字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        storeName: undefined,
        status: undefined,
        companyName: undefined,
        companyAddress: undefined,
        companyPhone: undefined,
        companyEmail: undefined,
        contactPerson: undefined,
        contactPhone: undefined,
        legalPerson: undefined,
        cardNo: undefined,
        cardPic: undefined,
        busLicenec: undefined,
        busLicenecPic: undefined,
        businessScope: undefined,
        orgPic: undefined,
        taxPic: undefined,
        bankUserName: undefined,
        bankAccount: undefined,
        bankName: undefined,
        bankNumber: undefined,
        bankAddress: undefined,
        bankPic: undefined,
        billingCycle: undefined,
        ismerge: undefined,
        serviceQq: undefined,
        reason: undefined,
        aveScore: undefined,
        effectiveTime: undefined,
        modifyTime: undefined,
        delTime: undefined,
        type: undefined,
        longitude: undefined,
        latitude: undefined,
        businessTime: undefined,
        busRoutes: undefined,
        avatarPicture: undefined,
        provinceId: undefined,
        cityId: undefined,
        districtId: undefined,
        companyDetailAddress: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList(1);
    this.getDicts("storeStatus").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("storeType").then(response => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    /** 查询店铺信息列表 */
    getList(status) {
      this.loading = true;
       this.queryStatus = status;
        this.queryParams.status = status;
      listTStoreInfo(this.queryParams).then(response => {
        this.TStoreInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 店铺状态

    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 店铺类型 0 普通店铺 1 加盟 2连锁字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
     //显示审核窗口
          showAuditDialogVisible(id) {
            this.tempStoreId = id;
            this.auditStatusValue = '2';
            this.form.reason = '';
            this.form.status = '';
            this.auditDialogVisible = true;
          },
          //保存审核
          auditData() {
            if (!this.auditStatusValue) {
              this.$message({
                message: '请选择审核状态！',
                type: 'warning'
              });
              return;
            }
            //审核通过
            if (this.auditStatusValue == 2) {
              this.pass();
            }
            //审核拒绝
            else {
              this.refuse();
            }
          },
          //通过
          pass() {
            if (this.isProcess) {
              return;
            }
            this.isProcess = true;
            let param = {
              id: this.tempStoreId,
              status: 2
            };
            passStoreAudit(param).then(response => {
              this.isProcess = false;
              if (response > 0) {
                this.$message({
                  type: 'success',
                  message: '审核店铺成功!'
                });
              } else {
                this.$message({
                  type: 'error',
                  message: '审核店铺失败!'
                });
              }
              this.auditDialogVisible = false;
              this.getList(this.queryStatus);
            });
          },
          //拒绝
          refuse() {
            this.$refs['form'].validate(valid => {
              if (valid) {
                if (this.isProcess) {
                  return
                }
                this.isProcess = true;
                this.form.id = this.tempStoreId;
                this.form.status = this.auditStatusValue;
                refuseStoreAudit(this.form).then(response => {
                  this.isProcess = false;
                  if (response > 0) {
                    this.$message({
                      type: 'success',
                      message: '审核拒绝成功!'
                    });
                  } else {
                    this.$message({
                      type: 'error',
                      message: '审核拒绝失败!'
                    });
                  }
                  this.auditDialogVisible = false;
                  this.getList(this.queryStatus);
                });
              }
            });
          },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        storeName: undefined,
        status: undefined,
        companyName: undefined,
        companyAddress: undefined,
        companyPhone: undefined,
        companyEmail: undefined,
        contactPerson: undefined,
        contactPhone: undefined,
        legalPerson: undefined,
        cardNo: undefined,
        cardPic: undefined,
        busLicenec: undefined,
        busLicenecPic: undefined,
        businessScope: undefined,
        orgPic: undefined,
        taxPic: undefined,
        bankUserName: undefined,
        bankAccount: undefined,
        bankName: undefined,
        bankNumber: undefined,
        bankAddress: undefined,
        bankPic: undefined,
        billingCycle: undefined,
        ismerge: undefined,
        delFlag: undefined,
        serviceQq: undefined,
        reason: undefined,
        aveScore: undefined,
        effectiveTime: undefined,
        createTime: undefined,
        modifyTime: undefined,
        delTime: undefined,
        type: undefined,
        longitude: undefined,
        latitude: undefined,
        businessTime: undefined,
        busRoutes: undefined,
        avatarPicture: undefined,
        provinceId: undefined,
        cityId: undefined,
        districtId: undefined,
        companyDetailAddress: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 0;
      this.getList(this.queryStatus);
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
      this.open = true;
      this.title = "添加店铺信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTStoreInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改店铺信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTStoreInfo(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList(this.queryStatus);
              }
            });
          } else {
            addTStoreInfo(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList(this.queryStatus);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除店铺信息编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTStoreInfo(ids);
        }).then(() => {
          this.getList(this.queryStatus);
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有店铺信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTStoreInfo(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
