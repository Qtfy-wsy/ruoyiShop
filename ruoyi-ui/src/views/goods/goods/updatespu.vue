<template>
  <div class="app-container">
    <el-form :model="spu" :rules="rules" ref="form" label-position="right" label-width="100px" style="margin-top: 20px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="商品分类">{{cateNames}}</el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="name">
            <span slot="label">SPU标题</span>
            <el-input v-model="spu.name" @change="changeName" maxlength="200"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="SPU副标题">
            <el-input v-model="spu.subTitle" maxlength="200">
              <el-button slot="append" icon="el-icon-thumb" style="color: #FFF;background-color: #67C23A;border-color: #67C23A" @click="setBatchSubtitle">应用到SKU</el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="price">
            <span slot="label">销售价格</span>
            <el-input v-model="spu.price" maxlength="8">
              <template slot="prepend">¥</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item prop="brandId">
            <span slot="label">商品品牌</span>
            <el-select v-model="spu.brandId" filterable placeholder="请选择品牌" style="width: 100%;">
              <el-option
                v-for="item in brands"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
            <router-link :to="{path:'/spumanager/spuconfig/brand'}">
              <el-link type="primary">立即去设置</el-link>
            </router-link>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="logisticsTemplateId" v-if="spu.isVirtual!='1'">
            <span slot="label">物流模板</span>
            <el-select v-model="spu.logisticsTemplateId" filterable placeholder="请选择物流模板" style="width: 100%">
              <el-option
                v-for="item in logisticsTemplates"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
            <router-link :to="{path:'/order/delevierManager/OmsLogisticsTemplate'}">
              <el-link type="primary">立即去设置</el-link>
            </router-link>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="服务支持">
        <el-checkbox-group v-model="serviceSupportChecked">
          <el-checkbox v-for="serviceSupport in serviceSupports" :label="serviceSupport.id" :key="serviceSupport.id">
            {{serviceSupport.name}}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="是否虚拟商品">
        {{spu.isVirtual == '0'?'否':'是'}}
      </el-form-item>
      <el-form-item label="是否上架">
        <el-switch
          style="vertical-align: sub"
          v-model="spu.shelvesStatus"
          active-value="1"
          inactive-value="0"
          active-color="#13ce66"
          inactive-color="#ff4949">
        </el-switch>
      </el-form-item>
      <el-form-item prop="spuImages">
        <span slot="label">SPU图片</span>
        <el-upload
          style="display: inline-block"
          accept=",.JPG,.jpeg,.png,.gif,.ico,.JPEG,.PNG,.GIF,.ICO,.jpg"
          :action="uploadApi"
          list-type="picture-card"
          :headers="headers"
          name="image"
          :file-list="images"
          :on-preview="handlePictureCardPreview"
          :on-remove="removeSpuPic"
          :on-success="uploadSpuPicSuccess">
          <el-tooltip effect="dark" content="建议图片尺寸800px * 800px" placement="right">
            <i class="el-icon-plus"></i>
          </el-tooltip>
        </el-upload>
        <div>
          <el-button type="success" icon="el-icon-thumb" @click="setBatchPic">应用到SKU</el-button>
        </div>
        <el-dialog :visible.sync="imageDialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
      </el-form-item>
      <el-form-item label="视频占位图片">
        <el-upload
          style="display: inline-block"
          accept=",.JPG,.jpeg,.png,.gif,.ico,.JPEG,.PNG,.GIF,.ICO,.jpg"
          :action="uploadApi"
          list-type="picture-card"
          :headers="headers"
          name="image"
          :file-list="videoPics"
          :before-upload="videoPicBeforeUpload"
          :on-preview="handlePictureCardPreview"
          :on-remove="removeVideoPic"
          :on-success="uploadVideoPicSuccess">
          <i class="el-icon-plus"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="介绍视频">
        <el-upload
          class="upload-demo"
          accept=".mp4"
          name="video"
          :action="uploadVideoApi"
          :file-list="videos"
          :headers="headers"
          :before-upload="videoBeforeUpload"
          :on-remove="removeVideo"
          :on-success="uploadVideoSuccess">
          <el-button size="small" type="primary">点击上传视频</el-button>
          <div slot="tip" class="el-upload__tip">建议上传mp4(H.264编码)文件，且不超过15M，支持H5，小程序</div>
        </el-upload>
      </el-form-item>
      <el-form-item v-if="attributes.length>0">
        <span slot="label"><span class="labelImportant">*</span>商品属性</span>
        <el-form :inline="true" class="demo-form-inline"
                 style="padding: 10px 10px 0; border-radius: 4px; border: dashed 1px #ccc">
          <el-form-item v-for="(attribute,index) in attributes" :key="attribute.id" :label="attribute.name"
                        label-width="95px"
                        style="margin-bottom: 10px">
            <el-select v-model="selectedAttributeValueIds[index]">
              <el-option
                v-for="attributeValue in attribute.attributeValues"
                :key="attributeValue.id"
                :label="attributeValue.value"
                :value="attributeValue.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </el-form-item>
      <el-form-item>
        <span slot="label"><span class="labelImportant">*</span>规格</span>
        <div style="display: flex; justify-content: space-between">
          <el-button type="primary" @click="specShow = true">新增规格</el-button>
          <div style="color: #606266">
            批量填充：
            <el-input v-model="batchWeight" maxlength="8" style="width: 100px" size="small" placeholder="重量"/>
            <el-input v-model="batchPrice" maxlength="8" style="width: 100px" size="small" placeholder="价格"/>
            <el-input v-model="batchStock" maxlength="8" style="width: 100px" size="small" placeholder="库存"/>
            <el-button type="primary" size="small" style="display: inline-block" @click="batchSetData">确定</el-button>
          </div>
        </div>
        <el-table
          v-if="selectedSpecNames.length>0"
          style="margin-top: 10px"
          :data="skus"
          border
          fit
          highlight-current-row
        >
          <el-table-column :key="specName" v-for=" (specName,index) in selectedSpecNames" :label="specName">
            <template slot-scope="scope">{{ scope.row.skuSpecValues[index].valueRemark }}</template>
          </el-table-column>
          <el-table-column label="重量(克)" width="110">
            <template slot-scope="scope">
              <el-input v-model="scope.row.weight" maxlength="8" style="width: 85px" size="small"/>
            </template>
          </el-table-column>
          <el-table-column label="价格(元)" width="180">
            <template slot-scope="scope">
              <el-input v-model="scope.row.price" maxlength="8" style="width: 85px" size="small"/>
              <el-button type="primary" size="small" style="display: inline-block"
                         @click="toShowSkuMemberPrice(scope.row)">会员价
              </el-button>
            </template>
          </el-table-column>
          <el-table-column label="会员价" width="65">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.skuMemberPrices.length>0" type="success">有</el-tag>
              <el-tag v-if="scope.row.skuMemberPrices.length==0" type="info">无</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="库存" width="120">
            <template slot-scope="scope">
              <el-input v-model="scope.row.stock" maxlength="8" style="width: 85px" size="small"/>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="140">
            <template slot-scope="scope">
              <el-button  type="text" icon="el-icon-edit" size="mini" @click="toEditSku(scope.row)">编辑</el-button>
              <el-button type="text" icon="el-icon-delete" size="mini" style="margin: 0" @click="delSku(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item label="PC版详情">
        <tinymce v-model="spu.pcDesc" :height="300"/>
      </el-form-item>
      <el-form-item label="移动版详情">
        <tinymce v-model="spu.mobileDesc" :height="300"/>
      </el-form-item>
      <!--<el-form-item label="seo标题">-->
      <!--<el-input v-model="spu.seoTitle" maxlength="200"/>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="seo关键字">-->
      <!--<el-input v-model="spu.seoKeywords" maxlength="200"/>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="seo描述">-->
      <!--<el-input v-model="spu.seoDesc" maxlength="200"/>-->
      <!--</el-form-item>-->
      <el-form-item>
        <el-button type="info" icon="el-icon-back" @click="backPage">返回商品列表</el-button>
        <el-button type="primary" @click="updateSpu">发布商品</el-button>
      </el-form-item>
    </el-form>

    <el-dialog title="新增规格" :visible.sync="specShow">
      <el-form label-position="right" label-width="100px" style="margin-top: 10px">
        <el-form-item v-for="(spec,index) in specs" :key="index" :label="spec.spec.name">
          <el-select style="width: 100%" v-model="selectedSpecValueIds[index]"
                     placeholder="请选择规格">
            <el-option
              v-for="specValue in spec.spec.specValues"
              :key="specValue.id"
              :label="specValue.name"
              :value="specValue.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="specShow = false">取消</el-button>
        <el-button type="primary" @click="saveSpecValues">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="设置会员价" :visible.sync="memberPriceShow">
      <el-alert title="注意！ 如果不设置会员价格，请把输入框全部清空。" type="warning" :closable="false" show-icon></el-alert>
      <el-table
        style="margin-top: 10px"
        :data="customerLevels"
        border
        fit
        highlight-current-row
      >
        <el-table-column label="会员等级">
          <template slot-scope="scope">{{ scope.row.name }}</template>
        </el-table-column>
        <el-table-column label="价格(元)">
          <template slot-scope="scope">
            <el-input v-model="scope.row.price" type="number" size="small"/>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="clearMemberPrice()" type="danger" style="float: left">清空会员价</el-button>
        <el-button @click="memberPriceShow = false">取消</el-button>
        <el-button type="primary" @click="setMemberPrice()">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog :before-close="handleClose" title="编辑规格详情" :visible.sync="editSkuShow">
      <el-alert title="注意！ 该页详情会在此SKU详情页显示，SKU图片如不上传则显示SPU默认图片。" type="warning" :closable="false" show-icon></el-alert>
      <el-form label-position="right" label-width="100px" style="margin-top: 10px">
        <el-form-item>
          <span slot="label"><span class="labelImportant">*</span>SKU标题</span>
          <el-input v-model="editSku.remark" maxlength="200">
            <template slot="prepend">
              <div
                style="width: 200px; overflow: hidden; word-wrap: break-word; word-break: break-all; text-overflow: ellipsis">
                {{editSku.name}}
              </div>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <span slot="label">SKU副标题</span>
          <el-input v-model="editSku.subTitle" maxlength="200"/>
        </el-form-item>
        <el-form-item>
          <span slot="label">SKU图片</span>
          <el-upload
            style="display: inline-block"
            accept=",.JPG,.jpeg,.png,.gif,.ico,.JPEG,.PNG,.GIF,.ICO,.jpg"
            :action="uploadApi"
            list-type="picture-card"
            :headers="headers"
            :file-list="editSku.skuImages"
            name="image"
            :on-remove="removeSkuPic"
            :on-preview="handlePictureCardPreview"
            :on-success="uploadSkuPicSuccess">
            <el-tooltip effect="dark" content="建议图片尺寸800px * 800px" placement="right">
              <i class="el-icon-plus"></i>
            </el-tooltip>
          </el-upload>
          <el-dialog :visible.sync="imageDialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
        <el-form-item>
          <span slot="label">预警库存</span>
          <el-input v-model="editSku.warningStock"
                    style="width: 200px"/>
        </el-form-item>
        <el-form-item label="是否批发">
          <el-switch
            style="vertical-align: sub"
            v-model="editSku.isBatchSku"
            active-value="1"
            inactive-value="0"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>

        <el-form-item v-show="editSku.isBatchSku=='1'">
          <span slot="label"><span class="labelImportant">*</span>起订量</span>
          <el-tooltip class="item" effect="dark" content="最多添加三级" placement="right">
            <el-button class="filter-item" size="medium" type="primary" icon="el-icon-plus" @click="addBatchPrice()">
              添加多级批发
            </el-button>
          </el-tooltip>
        </el-form-item>
        <el-form-item v-show="editSku.isBatchSku=='1'">
          <el-table
            :data="editSku.skuBatchList"
            border
            fit
            highlight-current-row
            style="line-height: 20px"
          >
            <el-table-column label="满件">
              <template slot-scope="scope">
                <el-input v-model="scope.row.batchNum" type="text" size="small" maxlength="8" style="width: 100%">
                  <template slot="append">件</template>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="销售价">
              <template slot-scope="scope">
                <el-input v-model="scope.row.batchPrice" type="text" size="small" maxlength="8" style="width: 100%">
                  <template slot="prepend">¥</template>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button v-if="scope.$index!=0" type="text" icon="el-icon-delete" size="mini"
                           @click.native.prevent="deleteBatchPrice(scope.$index)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeEditSku">取消</el-button>
        <el-button type="primary" @click="saveSku">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import Tinymce from '@/components/Tinymce'
  import {
    queryTypeDetail,
    querySpuById,
    queryAllBrands,
    queryAllLogisticsTemplates,
    queryAllLevels,
    queryAllServiceSupport,
    updateSpu
  } from '@/api/goods/updatespu';

 import { getToken } from '@/utils/auth'
  import {priceValidator, isMoney, isDigits, integer} from '@/utils/validate';

  export default {
    components: {Tinymce},
    data() {
      return {
        allSpecValues: [],// 所有的规格值
        selectedSpecValueIds: [],//选择的规格值id
        specs: [],// 规格信息
        specShow: false,//是否显示规格
        oldSpuSpecValues: [],// 老的规格信息
        isProcess: false, // 接口是否处理中
        batchWeight: '',// 批量填充的重量
        batchPrice: '',// 批量填充的价格
        batchStock: '',// 批量填充的库存
        editSku: {
          name: '',
          remark: '',
          subTitle: '',
          skuImages: [],
          warningStock: '',
          isBatchSku: '0',
          skuBatchList: new Array(),
        },// 修改的sku
        editSkuShow: false, // 编辑商品显示标记
        currentSku: {},// 当前选中的sku信息
        memberPriceShow: false,// 是否显示会员价格
        skus: [],// 单品信息
        selectedSpecNames: [],// 已经选择的规格名称
        selectedAttributeValueIds: [], // 选中的属性值数组
        uploadApi: process.env.VUE_APP_BASE_API+'/aliyun/oss/uploadToAliOss',  // 上传图片api
        uploadVideoApi: process.env.UPLOAD_API + "?name=video&type=1",  // 上传视频api
        headers: {
          Authorization: 'Bearer ' + getToken()
        }, // 认证的头部
        imageDialogVisible: false, // 图片放大
        dialogImageUrl: '', // 放大图片的地址
        images: [],// 单品的图片
        videoPics: [],// 视频封面图片
        videos: [], // 视频图片
        serviceSupportChecked: [],// 选中的服务支持
        attributes: [],// 商品属性
        cateNames: '',// 已选分类的名称
        brands: [],// 品牌
        serviceSupports: [],//服务支持
        spu: {
          brandId: '',
          id: '',
          mobileDesc: '',
          name: '',
          pcDesc: '',
          price: '',
          seoDesc: '',
          seoKeywords: '',
          seoTitle: '',
          shelvesStatus: '',
          skus: [],
          spuAttributeValues: [],
          spuImages: [],
          spuServiceSupports: [],
          spuSpecValues: [],
          subTitle: '',
          videoPic: '',
          video: '',
          logisticsTemplateId: '',
        },// 商品信息
        customerLevels: [],// 会员价格
        logisticsTemplates: [],// 所有的物流模版
        rules: {
          name: [
            {
              required: true,
              message: '请输入商品标题',
              trigger: 'blur'
            }
          ],
          price: [
            {
              required: true,
              trigger: 'blur',
              validator: priceValidator
            }
          ],
          brandId: [
            {
              required: true,
              message: '请选择商品品牌',
              trigger: 'blur'
            }
          ],
          spuImages: [
            {
              required: true,
              message: '请上传商品图片',
              trigger: 'blur'
            }
          ],
          logisticsTemplateId: [
            {
              required: true,
              message: '请选择物流模板',
              trigger: 'blur'
            }
          ],
        }, // 验证规则
      }
    },
    created() {
      this.querySpu();
      this.queryCutomerLevels();
      this.queryLogisticsTemplates();
    },
    methods: {
      // 查询会员等级
      queryCutomerLevels() {
        queryAllLevels().then(res => {
          if (res && res.length > 0) {
            res.forEach(level => {
              this.customerLevels.push({
                id: level.id,
                name: level.name,
                price: '',
                discount: level.discount
              });
            })
          }
        })
      },
      // 查询所有的物流模版
      queryLogisticsTemplates() {
        queryAllLogisticsTemplates().then(res => {
          this.logisticsTemplates = res;
        })
      },
      // 查询商品信息
      querySpu() {
        querySpuById(this.$route.query.id).then(res => {
          if (res) {
            this.cateNames = res.firstCategory.name + '/' + res.secondCategory.name + '/' + res.thirdCategory.name;
            this.spu.id = res.id;
            this.spu.mobileDesc = res.mobileDesc;
            this.spu.name = res.name;
            this.spu.pcDesc = res.pcDesc;
            this.spu.price = res.price;
            this.spu.seoDesc = res.seoDesc;
            this.spu.seoKeywords = res.seoKeywords;
            this.spu.seoTitle = res.seoTitle;
            this.spu.shelvesStatus = res.shelvesStatus;
            this.spu.subTitle = res.subTitle;
            this.oldSpuSpecValues = res.spuSpecValues;
            this.spu.isVirtual = res.isVirtual;
            this.spu.videoPic = res.videoPic;
            this.spu.video = res.video;
            this.spu.logisticsTemplateId = res.logisticsTemplateId;

            if (res.video) {
              this.videos = [{name: res.video, url: res.video}];
            }

            if (res.videoPic) {
              this.videoPics = [{name: res.videoPic, url: res.videoPic}];
            }

            this.images = res.spuImages.map(spuImage => {
              return {url: spuImage.url};
            })

            this.selectedSpecNames = Array.from(new Set(res.spuSpecValues.map(specValue => {
              return specValue.spec.name;
            })))

            this.skus = res.skus.map(sku => {
              const newsku = {
                id: sku.id,
                skuNo: sku.skuNo,
                stock: sku.stock,
                price: sku.price,
                weight: sku.weight,
                delFlag: '0',
                warningStock: sku.warningStock,
                isBatchSku: sku.isBatchSku,
                name: sku.name,
                subTitle: sku.subTitle,
                skuBatchList: [],
                skuImages: [],
                skuMemberPrices: [],
                skuSpecValues: [],
                remark: ''
              }

              let oldSkuName = newsku.name;
              newsku.name = res.name;
              newsku.remark = oldSkuName.replace(res.name, '');

              // 设置单品图片
              newsku.skuImages = sku.skuImages.map(image => {
                return {delFlag: '0', url: image.url}
              })

              // 设置单品会员价格
              if (sku.skuMemberPrices && sku.skuMemberPrices.length > 0) {
                newsku.skuMemberPrices = sku.skuMemberPrices.map(memberPrice => {
                  return {
                    memberLevelId: memberPrice.memberLevelId,
                    price: memberPrice.price,
                  }
                })
              }

              // 设置单品规格信息
              newsku.skuSpecValues = sku.skuSpecValues.map(specValue => {
                return {
                  specId: specValue.specId,
                  specValueId: specValue.specValueId,
                  valueRemark: specValue.valueRemark
                };
              })

              // 设置单品的起批价格
              if (sku.skuBatchList && sku.skuBatchList.length > 0) {
                newsku.skuBatchList = sku.skuBatchList.map(batch => {
                  return {batchNum: batch.batchNum, batchPrice: batch.batchPrice}
                })
              }

              return newsku;
            })

            res.spuSpecValues.forEach(spec => {
              if (this.specs.filter(s => s.specId == spec.specId).length == 0) {
                spec.spec.specValues.forEach(value => {
                  this.allSpecValues.push(value);
                })
                this.specs.push(spec);
                // 默认选中第一个
                this.selectedSpecValueIds.push(spec.spec.specValues[0].id);
              }
            })

            this.queryType(res.typeId, res.spuAttributeValues);
            this.queryAllBrands(res.brandId);
            this.queryAllServiceSupport(res.spuServiceSupports);
          }
        })
      },
      // 加载商品类型
      queryType(typeId, spuAttributeValues) {
        queryTypeDetail(typeId).then(res => {
          if (res) {
            this.attributes = res.attributes;
            if (spuAttributeValues && spuAttributeValues.length > 0) {
              spuAttributeValues.forEach(attribute => {
                this.selectedAttributeValueIds.push(attribute.attributeValueId)
              })
            }
          }
        });
      },
      // 查询所有的品牌
      queryAllBrands(brandId) {
        queryAllBrands().then(res => {
          this.brands = res;
          this.spu.brandId = brandId;
        })
      },
      // 查询所有服务支付
      queryAllServiceSupport(spuServiceSupports) {
        queryAllServiceSupport().then(res => {
          this.serviceSupports = res;
          if (spuServiceSupports && spuServiceSupports.length > 0) {
            this.serviceSupportChecked = spuServiceSupports.map(support => {
              return support.serviceSupportId
            })
          }
        })
      },
      // 放大图片
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url
        this.imageDialogVisible = true
      },
      // 上传商品图片
      uploadSpuPicSuccess(res) {
        this.images.push({url: res});
      },
      // 删除图片
      removeSpuPic(file) {
        this.images = this.images.filter(image => image.url != file.url);
      },
      // 显示会员价格
      toShowSkuMemberPrice(sku) {
        this.currentSku = sku;
        this.memberPriceShow = true;
        this.currentSku.skuMemberPrices.forEach(skuMemberPrice => {
          if (skuMemberPrice) {
            this.customerLevels.filter(level => level.id == skuMemberPrice.memberLevelId)[0].price = skuMemberPrice.price;
          }
        })
      },
      // 设置会员价格
      setMemberPrice() {
        this.currentSku.skuMemberPrices = new Array();
        this.customerLevels.forEach(level => {
          if (level.price) {
            this.currentSku.skuMemberPrices.push({memberLevelId: level.id, price: level.price});
          }
        })
        this.memberPriceShow = false;
      },
      // 准备编辑商品
      toEditSku(sku) {
        this.currentSku = sku;
        this.editSkuShow = true;
        this.editSku = {
          name: sku.name,
          remark: sku.remark,
          subTitle: sku.subTitle,
          warningStock: sku.warningStock,
          isBatchSku: sku.isBatchSku,
          skuBatchList: new Array(),
        };

        if (sku.warningStock == '-1') {
          this.editSku.warningStock = '';
        } else {
          this.editSku.warningStock = sku.warningStock;
        }

        this.editSku.skuImages = sku.skuImages.map(skuImage => {
          return {
            delFlag: '0',
            url: skuImage.url
          };
        });

        // 如果是起批则设置起批价格
        if (sku.isBatchSku == '1') {
          this.editSku.skuBatchList = sku.skuBatchList.map(skuBatch => {
            return {
              batchNum: skuBatch.batchNum,
              batchPrice: skuBatch.batchPrice
            };
          });
        }
      },
      delSku(index) {
        this.$confirm('确定要删除此规格商品吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.skus.splice(index, 1)
          this.$message({
            type: 'success',
            message: '删除商品成功!'
          })
        })
      },
      // 保存单品
      saveSku() {

        // 检查图片
        if (this.editSku.skuImages.length == 0) {
          this.$message({
            type: 'error',
            message: '请上传单品图片!'
          });
          return false;
        }

        // 校验库存预警是否合法
        if (this.editSku.warningStock != '') {
          if (!isDigits(this.editSku.warningStock)) {
            this.$message({
              type: 'error',
              message: '库存预警只能是整数!'
            });
            return false;
          }
        }

        // 如果是起批商品则判断合法性
        if (this.editSku.isBatchSku == '1') {
          if (!this.editSku.skuBatchList || this.editSku.skuBatchList.length == 0) {
            this.$message({
              type: 'error',
              message: '至少输入一个起批价格!'
            });
            return false;
          }

          for (let i = 0; i < this.editSku.skuBatchList.length; i++) {
            if (!integer(this.editSku.skuBatchList[i].batchNum)) {
              this.$message({
                type: 'error',
                message: '起批量必须是正整数!'
              });
              return false;
            }


            if (!isMoney(this.editSku.skuBatchList[i].batchPrice)) {
              this.$message({
                type: 'error',
                message: '请输入正确的起批价格!'
              });
              return false;
            }
          }
        }


        this.currentSku.name = this.editSku.name;
        this.currentSku.remark = this.editSku.remark;
        this.currentSku.subTitle = this.editSku.subTitle;
        this.currentSku.isBatchSku = this.editSku.isBatchSku;
        this.currentSku.skuImages = this.editSku.skuImages.map(image => {
          return {
            url: image.url,
            delFlag: '0',
          }
        })

        // 设置预警库存
        if (this.editSku.warningStock == '') {
          this.currentSku.warningStock = '-1';
        } else {
          this.currentSku.warningStock = this.editSku.warningStock;
        }

        // 如果是起批商品则设置起批价格
        if (this.currentSku.isBatchSku == '1') {
          this.currentSku.skuBatchList = this.editSku.skuBatchList.map(skuBatch => {
            return {
              batchNum: skuBatch.batchNum,
              batchPrice: skuBatch.batchPrice
            };
          });
        } else {
          this.currentSku.skuBatchList = new Array();
        }

        this.clearCurrentSku();
        this.editSkuShow = false;
      },
      // 清理当前编辑的sku
      clearCurrentSku() {
        this.currentSku = {};
        this.editSku = {
          name: '',
          remark: '',
          subTitle: '',
          skuImages: [],
          warningStock: '',
          isBatchSku: '0',
          skuBatchList: new Array(),
        }
      },
      // 关闭编辑商品
      handleClose(done) {
        this.clearCurrentSku();
        done();
      },
      // 批量填充价格 库存和重量
      batchSetData() {

        if (this.skus.length == 0) {
          this.$message({
            type: 'error',
            message: '请先选择规格值生成商品!'
          });
          return;
        }

        // 判断重量是否合法
        if (this.batchWeight) {
          if (!isDigits(this.batchWeight)) {
            this.$message({
              type: 'error',
              message: '重量只能填写整数'
            });
            return;
          }
        }

        // 判断库存是否合法
        if (this.batchStock) {
          if (!isDigits(this.batchStock)) {
            this.$message({
              type: 'error',
              message: '库存只能填写整数'
            });
            return;
          }
        }

        // 判断金额是否合法
        if (this.batchPrice) {
          if (!isMoney(this.batchPrice)) {
            this.$message({
              type: 'error',
              message: '请输入合法的金额!'
            });

            return;
          }
        }

        this.skus.forEach(sku => {
          // 批量填充重量
          if (this.batchWeight) {
            sku.weight = this.batchWeight;
          }

          // 批量填充价格
          if (this.batchPrice) {
            sku.price = this.batchPrice;
          }

          // 批量填充库存
          if (this.batchStock) {
            sku.stock = this.batchStock;
          }
        })
        this.$message({
          type: 'success',
          message: '批量填充成功!'
        });
      },
      // 批量设置副标题
      setBatchSubtitle() {
        if (this.skus.length == 0) {
          this.$message({
            type: 'error',
            message: '请先选择规格值生成商品!'
          });
          return;
        }
        this.skus.forEach(sku => {
          sku.subTitle = this.spu.subTitle
        })
        this.$message({
          type: 'success',
          message: '批量填充成功!'
        });
      }
      ,
      // 批量设置图片
      setBatchPic() {
        if (this.skus.length == 0) {
          this.$message({
            type: 'error',
            message: '请先选择规格值生成商品!'
          });
          return;
        }

        if (this.images.length == 0) {
          this.$message({
            type: 'error',
            message: '请先上传商品图片!'
          });
          return;
        }

        this.skus.forEach(sku => {
          sku.skuImages = this.images.map(image => {
            return {
              url: image.url,
              delFlag: '0'
            }
          })
        })
        this.$message({
          type: 'success',
          message: '批量填充成功!'
        });
      },
      // 关闭编辑单品
      closeEditSku() {
        this.clearCurrentSku();
        this.editSkuShow = false;
      },
      // 校验单品信息
      validateSkus() {
        for (let i = 0; i < this.skus.length; i++) {
          // 判断重量是否合法
          if (!isDigits(this.skus[i].weight)) {
            this.$message({
              type: 'error',
              message: '重量只能填写整数'
            });
            return false;
          }

          // 判断库存是否合法
          if (!isDigits(this.skus[i].stock)) {
            this.$message({
              type: 'error',
              message: '库存只能填写整数'
            });
            return false;
          }

          // 判断金额是否合法
          if (!isMoney(this.skus[i].price)) {
            this.$message({
              type: 'error',
              message: '请输入合法的金额!'
            });

            return false;
          }
        }

        return true;
      }
      ,
      // 更新商品
      updateSpu() {

        // 判断是否有单品 没有则提示报错
        if (!this.skus || this.skus.length == 0) {
          this.$message({
            type: 'error',
            message: '请选择规格信息!'
          });
          return;
        }

        // 校验单品是否合法
        if (!this.validateSkus()) {
          return;
        }

        this.spu.skus = this.skus;
        // 设置商品图片
        this.spu.spuImages = this.images.map(image => {
          return {
            url: image.url,
            delFlag: '0'
          }
        })

        // 设置视频地址
        if (this.videos && this.videos.length > 0) {
          this.spu.video = this.videos[0].url;
        } else {
          this.spu.video = '';
        }

        // 设置视频图片地址
        if (this.videoPics && this.videoPics.length > 0) {
          this.spu.videoPic = this.videoPics[0].url;
        } else {
          this.spu.videoPic = '';
        }


        // 设置商品属性
        this.spu.spuAttributeValues = this.selectedAttributeValueIds.map((selectedId, index) => {
          let attribute = this.attributes[index];
          let attributeValue = attribute.attributeValues.filter(value => value.id == selectedId)[0];
          return {
            attributeId: attribute.id,
            attributeName: attribute.name,
            attributeValue: attributeValue.value,
            attributeValueId: attributeValue.id,
            delFlag: '0'
          };
        })

        // 设置商品服务支持
        if (this.serviceSupportChecked && this.serviceSupportChecked.length > 0) {
          this.spu.spuServiceSupports = this.serviceSupportChecked.map(service => {
            return {serviceSupportId: service};
          })
        }

        // 设置商品的规格值
        this.spu.spuSpecValues = new Array();
        this.skus.forEach(sku => {
          sku.skuSpecValues.forEach(spcValue => {
            const newSpecValue = {
              specId: spcValue.specId,
              specValueId: spcValue.specValueId,
              valueRemark: spcValue.valueRemark,
              url: ''
            }

            // 找出规格值原来的设置 如果有的话找出原来设置的图片
            const oldSpecValue = this.oldSpuSpecValues.filter(old => old.specValueId == newSpecValue.specValueId)[0];
            if (oldSpecValue) {
              newSpecValue.url = oldSpecValue.url;
            }

            // 去除重复的规格值
            if (!(this.spu.spuSpecValues.filter(value => value.specValueId == newSpecValue.specValueId)[0])) {
              this.spu.spuSpecValues.push(newSpecValue)
            }

          })
        })

        this.$refs['form'].validate(valid => {
          if (!this.isProcess && valid) {
            this.isProcess = true;
            updateSpu(this.spu).then((res) => {
              this.isProcess = false;
              if (res == -1) {
                this.$message({
                  type: 'error',
                  message: '单品有促销与会员价互斥!'
                });
              } else if (res == -2) {
                this.$message({
                  type: 'error',
                  message: '单品有促销与批发互斥!'
                });
              } else if (res == -3) {
                this.$message({
                  type: 'error',
                  message: '批发商品不能有会员价!'
                });
              } else {
                this.$message({
                  type: 'success',
                  message: '更新成功!'
                });
                this.$router.push({path: '/goods/goodsManager/goods'})
              }
            })
          }
        });

      },
      backPage() {
        this.$router.back(-1)
      },
      // 保存选中的规格值
      saveSpecValues() {
        // 已经选择的规格值组合
        const allSpecValues = this.getAllSpecValues();

        // 当前选中的规格值组合
        let currentSpecValues = '';
        this.selectedSpecValueIds.forEach(id => {
          currentSpecValues += id + ',';
        })

        // 判断规格值的组合是否存在
        if (allSpecValues.filter(value => value == currentSpecValues).length != 0) {
          this.$message({
            type: 'error',
            message: '选中的规格值组合已经存在!'
          });
          return;
        }

        let sku = {};
        sku.skuNo = Date.parse(new Date()) + 0;
        sku.stock = '';
        sku.price = this.spu.price;
        sku.weight = '';
        sku.delFlag = '0';
        sku.skuSpecValues = new Array();
        sku.warningStock = '-1';
        sku.skuMemberPrices = new Array();
        sku.skuImages = new Array();
        sku.isBatchSku = '0';
        sku.skuBatchList = new Array();
        sku.skuImages = new Array();
        sku.name = this.spu.name;
        sku.subTitle = this.spu.subTitle;

        let specAllValue = '';

        // 设置单品的规格值
        this.selectedSpecValueIds.forEach((id, index) => {
          const specValue = this.allSpecValues.filter(value => value.id == id)[0];
          let skuSpecValue = {};
          skuSpecValue.specId = specValue.specId;
          skuSpecValue.specValueId = specValue.id;
          skuSpecValue.valueRemark = specValue.name;
          sku.skuSpecValues.push(skuSpecValue);
          if (index == 0) {
            specAllValue += specValue.name;
          } else {
            specAllValue += "-" + specValue.name;
          }
        })

        sku.remark =  specAllValue ;

        // 计算会员价格
        if (this.customerLevels.length > 0) {
          this.customerLevels.forEach(customerlevel => {
            const skuMemberPrice = {};
            skuMemberPrice.memberLevelId = customerlevel.id;
            skuMemberPrice.price = (parseFloat(customerlevel.discount) * sku.price).toFixed(2);
            sku.skuMemberPrices.push(skuMemberPrice)
          })
        }

        // 设置单品的图片
        this.images.forEach(image => {
          sku.skuImages.push({
            url: image.url,
            delFlag: '0'
          })
        })

        this.skus.push(sku);
        this.specShow = false;
      },
      // 获得已经选择的规格值组合
      getAllSpecValues() {
        const allSpecValues = [];
        this.skus.forEach(sku => {
          let values = '';
          sku.skuSpecValues.forEach(specValue => {
            values += specValue.specValueId + ','
          })
          allSpecValues.push(values)
        })

        return allSpecValues;
      },
      // 改变商品名称
      changeName(name) {
        // 改变商品名称的时候批量修改单品的名称
        if (this.skus && this.skus.length > 0) {
          this.skus.forEach(sku => {
            sku.name = name;
          })
        }
      },
      // 上传单品图片
      uploadSkuPicSuccess(res) {
        this.editSku.skuImages.push({
          url: res,
          delFlag: '0'
        });
      }
      ,
      // 删除单品图片
      removeSkuPic(file) {
        this.editSku.skuImages = this.editSku.skuImages.filter(skuImage => skuImage.url != file.url);
      }
      ,
      // 增加起批量
      addBatchPrice() {
        if (this.editSku.skuBatchList.length < 3) {
          this.editSku.skuBatchList.push({
            batchNum: '',
            batchPrice: ''
          });
        }
      },
      // 删除起批量
      deleteBatchPrice(index) {
        this.editSku.skuBatchList.splice(index, 1)
      },
      // 清空会员价格
      clearMemberPrice() {
        this.customerLevels.forEach(skuMemberPrice => {
          skuMemberPrice.price = '';
        })
      },
      // 上传视频成功
      uploadVideoSuccess(res) {
        this.videos = [{name: res, url: res}];
      },
      // 删除视频
      removeVideo(file) {
        if (file && file.status === "success") {
          this.videos = [];
        }
      },
      // 上传视频图片成功
      uploadVideoPicSuccess(res) {
        this.videoPics = [{name: res, url: res}];
      },
      // 删除视频图片
      removeVideoPic(file) {
        if (file && file.status === "success") {
          this.videoPics = [];
        }
      },
      // 视频上传前判断是否已经有视频如果有视频则提示只能上传一张
      videoBeforeUpload() {
        if (this.videos && this.videos.length > 0) {
          this.$message({
            type: 'error',
            message: '只能上传一个视频!'
          });
          return false;
        }
        return true;
      },
      // 视频封面图片上传前判断是否已经有视频封面图片如果有则提示只能上传一张
      videoPicBeforeUpload() {
        if (this.videoPics && this.videoPics.length > 0) {
          this.$message({
            type: 'error',
            message: '只能上传一个视频封面图片!'
          });
          return false;
        }
        return true;
      }
    }

  }
</script>
