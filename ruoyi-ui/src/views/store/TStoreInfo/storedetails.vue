<template>
  <div class="app-container">
    <div style="line-height: 50px">商家信息</div>
    <div class="detailsTable">
      <el-row>
        <el-col :span="8">商家编号：{{storeInfo.id}}</el-col>
        <el-col :span="8">店铺名称：{{storeInfo.storeName | isSubmit}}</el-col>
        <el-col :span="8">公司名称：{{storeInfo.companyName | isSubmit}}</el-col>
      </el-row>
      <el-row>
        <el-col :span="8">公司邮箱：{{storeInfo.companyEmail | isSubmit }}</el-col>
        <el-col :span="8">公司电话：{{storeInfo.companyPhone | isSubmit}}</el-col>
        <el-col :span="8">公司地址：{{storeInfo.companyAddress | isSubmit}}</el-col>
      </el-row>
      <el-row>
        <el-col :span="8">公司联系人：{{storeInfo.contactPerson | isSubmit}}</el-col>
        <el-col :span="8">法定代表人身份证号：{{storeInfo.cardNo | isSubmit}}</el-col>
        <el-col :span="8">法人身份证电子版：
          <el-link
            v-show="showCarPic"
            :underline="false"
            type="success"
            :href="carPic"
            target="_blank"
            style="vertical-align: bottom"
          ><i class="el-icon-view el-icon--right"/> 查看
          </el-link>
          {{carPic | noPhoto}}
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">营业执照副本电子版：
          <el-link
            v-show="showBusLicensePic"
            :underline="false"
            type="success"
            :href="busLicensePic"
            target="_blank"
            style="vertical-align: bottom"
          ><i class="el-icon-view el-icon--right"/> 查看
          </el-link>
          {{busLicensePic | noPhoto}}
        </el-col>
        <el-col :span="8">组织机构代码证电子版：
          <el-link
            v-show="showOrgPic"
            :underline="false"
            type="success"
            :href="orgPic"
            target="_blank"
            style="vertical-align: bottom"
          ><i class="el-icon-view el-icon--right"/> 查看
          </el-link>
          {{orgPic | noPhoto}}
        </el-col>
        <el-col :span="8">税务登记证电子版：
          <el-link
            v-show="showTaxPic"
            :underline="false"
            type="success"
            :href="taxPic"
            target="_blank"
            style="vertical-align: bottom"
          ><i class="el-icon-view el-icon--right"/> 查看
          </el-link>
          {{taxPic | noPhoto}}
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">银行开户名：{{storeInfo.bankUserName | isSubmit}}</el-col>
        <el-col :span="8">公司银行账号：{{storeInfo.bankAccount | isSubmit}}</el-col>
        <el-col :span="8">开户行支行名称：{{storeInfo.bankName | isSubmit}}</el-col>
      </el-row>
      <el-row>
        <el-col :span="8">开户行支行银行号：{{storeInfo.bankNumber | isSubmit}}</el-col>
        <el-col :span="8">开户行支行所在地：{{storeInfo.bankAddress | isSubmit}}</el-col>
        <el-col :span="8">银行开户许可证电子版：
          <el-link
            v-show="showBankPic"
            :underline="false"
            type="success"
            :href="bankPic"
            target="_blank"
            style="vertical-align: bottom"
          ><i class="el-icon-view el-icon--right"/> 查看
          </el-link>
          {{bankPic | noPhoto}}
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">营业时间：{{storeInfo.businessTime | isSubmit}}</el-col>
        <el-col :span="8">公交线路：{{storeInfo.busRoutes | isSubmit}}</el-col>
        <el-col :span="8">店铺头像：
          <el-link
            v-show="showAvatarPicture"
            :underline="false"
            type="success"
            :href="avatarPicture"
            target="_blank"
            style="vertical-align: bottom"
          ><i class="el-icon-view el-icon--right"/> 查看
          </el-link>
          {{avatarPicture | noPhoto}}
        </el-col>
      </el-row>
    </div>
    <div style="line-height: 50px">签约分类</div>
    <div class="detailsTable" style="padding: 10px 10px 5px 10px">
      <el-tag v-for="iteam in threeCategoryList" v-bind:key="iteam"
      >{{iteam}}
      </el-tag>
    </div>
    <div style="line-height: 50px">经营范围</div>
    <div class="detailsTable" style="padding: 10px 10px 5px 10px">
      <el-tag
        v-for="iteam in businessScopeags" v-bind:key="iteam"
      >{{iteam}}
      </el-tag>
    </div>
    <div style="line-height: 50px">签约品牌</div>
    <div class="detailsTable" style="padding: 10px 10px 5px 10px">
      <el-tag v-for="iteam in storeBrandList" v-bind:key="iteam">{{iteam}}</el-tag>
    </div>
    <div style="line-height: 50px">自定义品牌</div>
    <el-table
      :data="mySelfBrandList"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="品牌名称">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column label="品牌LOGO">
        <template slot-scope="scope"><img :src="scope.row.url" height="36" style="vertical-align: middle">
        </template>
      </el-table-column>
      <el-table-column label="品牌证书">
        <template slot-scope="scope"><a :href="scope.row.certificatUrl" target="_blank"><img
          :src="scope.row.certificatUrl"
          height="36"
          style="vertical-align: middle"></a>
        </template>
      </el-table-column>
    </el-table>
    <div style="text-align: center; margin-top: 20px">
      <router-link to="/storemanager/storesaudit/storeslist">
        <el-button type="info" icon="el-icon-back">返回商家列表</el-button>
      </router-link>
    </div>
  </div>
</template>

<script>

  import {
    storeDetailInfoByStoreId,  //根据店铺id查询店铺详情信息
  } from "@/api/store/TStoreInfo";


  // 店铺状态和品牌状态的映射 key 店铺状态  value 品牌状态
  const statusMap = {"1": "0", "2": "1", "3": "0"};

  export default {
    data() {
      return {
        listLoading: true,
        listQuery: {
          storeId: '',
          status: '',
        }, // 查询参数
        showCarPic: true, //法人身份证电子版组件显示标记
        showBusLicensePic: true,//营业执照显示标记
        showOrgPic: true,//组织机构代码电子版显示标记
        showTaxPic: true,//税务登记证电子版显示标记
        showBankPic: true,//银行开户许可证电子版显示标记
        showAvatarPicture: true,//店铺头像
        storeInfo: {
          id: '', //编号
          storeName: '', //店铺名称
          companyEmail: '', //公司邮箱
          companyName: '', //公司名称
          businessTime: '', //营业时间
          busRoutes: '',   //公交路线
          bankUserName: '', //银行开户名
          bankAccount: '', //公司银行账号
          bankName: '', //开户银行支行名称
          bankNumber: '', //支行银行号
          bankAddress: '', //开户银行所在地
          companyPhone: '', //公司电话
          companyAddress: '', //公司地址
          contactPerson: '', //公司联系人
          cardNo: '', //法人身份证号码
        },
        carPic: '', //法人身份证电子版
        busLicensePic: '',//营业执照电子版
        orgPic: '',//组织机构代码电子版
        taxPic: '', //税务登记证电子版
        bankPic: '',//银行开户许可证电子版
        avatarPicture: '',//店铺头像
        mySelfBrandList: null, //自定义品牌
        businessScopeags: [],//经营范围
        threeCategoryList: [],//签约分类
        storeBrandList: [], //签约品牌
      }
    },
    created() {
      this.getList();
    },
    filters: {
      isSubmit(value) {
        if (value == null || value == '') {
          return '未提交';
        }
        return value;
      },
      noPhoto(value) {
        if (value == null || value == '') {
          return '未提交';
        }
        return '';
      }
    },
    methods: {
      getList() {
        this.listQuery.storeId = this.$route.query.storeId;
        this.listQuery.status = statusMap[this.$route.query.status];
        storeDetailInfoByStoreId(this.listQuery).then(response => {

          //店铺信息
          let resStoreInfo = response.storeInfo;
          this.storeInfo.businessTime = resStoreInfo.businessTime;
          this.storeInfo.id = resStoreInfo.id;
          this.storeInfo.companyEmail = resStoreInfo.companyEmail;
          this.storeInfo.storeName = resStoreInfo.storeName;
          this.storeInfo.companyName = resStoreInfo.companyName;
          this.storeInfo.busRoutes = resStoreInfo.busRoutes;
          this.storeInfo.bankUserName = resStoreInfo.bankUserName;
          this.storeInfo.bankAccount = resStoreInfo.bankAccount;
          this.storeInfo.bankName = resStoreInfo.bankName;
          this.storeInfo.bankNumber = resStoreInfo.bankNumber;
          this.storeInfo.bankAddress = resStoreInfo.bankAddress;
          this.storeInfo.companyPhone = resStoreInfo.companyPhone;
          this.storeInfo.companyAddress = resStoreInfo.companyAddress;
          this.storeInfo.contactPerson = resStoreInfo.contactPerson;
          this.storeInfo.cardNo = resStoreInfo.cardNo;


          //法人身份证电子版
          if (resStoreInfo.carPic) {
            this.carPic = resStoreInfo.carPic;
          } else {
            this.showCarPic = false;
          }
          //营业执照副本电子版
          if (resStoreInfo.busLicensePic) {
            this.busLicensePic = resStoreInfo.busLicensePic;
          } else {
            this.showBusLicensePic = false;
          }
          //组织机构代码电子版
          if (resStoreInfo.orgPic) {
            this.orgPic = resStoreInfo.orgPic;
          } else {
            this.showOrgPic = false;
          }
          //税务登记证电子版
          if (resStoreInfo.taxPic) {
            this.taxPic = resStoreInfo.taxPic;
          } else {
            this.showTaxPic = false;
          }
          //银行开户许可证电子版
          if (resStoreInfo.bankPic) {
            this.bankPic = resStoreInfo.bankPic;
          } else {
            this.showBankPic = false;
          }
          //店铺头像
          if (resStoreInfo.avatarPicture) {
            this.avatarPicture = resStoreInfo.avatarPicture;
          } else {
            this.showAvatarPicture = false;
          }


          //自定义品牌
          this.mySelfBrandList = response.mySelfBrandList;
          this.listLoading = false;

          //经营范围
          this.businessScopeags = resStoreInfo.businessScope.split(',');

          //签约分类
          for (let i = 0; i < response.threeCategoryList.length; i++) {
            this.threeCategoryList.push(response.threeCategoryList[i].name);
          }

          //签约品牌
          for (let i = 0; i < response.storeBrandList.length; i++) {
            this.storeBrandList.push(response.storeBrandList[i].name);
          }


        });
      }
    }
  }
</script>
