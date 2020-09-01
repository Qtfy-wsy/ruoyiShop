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
        <el-form-item label="广告图">
                      <el-upload
                        class="avatar-uploader"
                        style="width: auto; height: auto"
                        :action="uploadApi"
                        :show-file-list="false"
                        :on-success="uploadMobilePanicPicSuccess"
                        :headers="headers"
                        name="image"
                        accept=",.JPG,.jpeg,.png,.gif,.ico,.JPEG,.PNG,.GIF,.ICO,.jpg">
                        <img v-if="form.modifyName" :src="form.modifyName" style="max-height: 100px; display: block">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                      </el-upload>
                    </el-form-item>
                <el-form-item prop="delName">
                          <span slot="label">广告地址</span>
                          <el-input v-model="form.delName" maxlength="10"/>
                           <el-button class="isLink"   type="primary"
                                                                plain icon="el-icon-link"
                                                                @click="toEditLink(form.delName)"/>
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
              :label="item.nickName"
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
 <el-dialog title="链接" :visible.sync="dialogLinkVisible" width="80%">
          <el-tabs v-model="tabActiveName" @tab-click="changeTab()" type="card">
            <!-- 当前链接 -->
            <el-tab-pane label="当前链接" name="0" style="min-height: 300px">
              <div v-if="this.linkRef.actionType=='2'" style="line-height: 40px">分类：{{this.linkedCate.name}}</div>
              <div v-if="this.linkRef.actionType=='6'" style="line-height: 40px">功能链接：{{this.linkedFunction}}</div>
              <div v-if="this.linkRef.actionType=='7'" style="line-height: 40px">自定义链接：{{this.linkRef.actionParamId}}</div>
              <el-table
                v-if="this.linkRef.actionType =='1'"
                :data="linkedSkus"
                border
                fit
                highlight-current-row
              >
                <el-table-column label="图片" width="80">
                  <template slot-scope="scope"><img :src="scope.row.url" width="50" height="50"></template>
                </el-table-column>
                <el-table-column label="货品名称" min-width="250">
                  <template slot-scope="scope">{{ scope.row.name }}</template>
                </el-table-column>
                <el-table-column label="货品规格">
                  <template slot-scope="scope">
                    <span v-html="getSpecValues(scope.row.skuSpecValues)"></span>
                  </template>
                </el-table-column>
                <el-table-column label="销售价">
                  <template slot-scope="scope">{{ scope.row.price.toFixed(2) }}</template>
                </el-table-column>
                <el-table-column label="店铺名称">
                  <template slot-scope="scope">{{ scope.row.storeName }}</template>
                </el-table-column>
              </el-table>
              <el-table
                v-if="this.linkRef.actionType =='4'"
                :data="linkedThemtics"
                border
                fit
                highlight-current-row
              >
                <el-table-column label="专题标题" min-width="250">
                  <template slot-scope="scope">{{ scope.row.title }}</template>
                </el-table-column>
                <el-table-column label="是否启用" width="80">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.isUse == '0'" type="success">是</el-tag>
                    <el-tag v-if="scope.row.isUse == '1'" type="info">否</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="创建时间" width="160">
                  <template slot-scope="scope">{{ scope.row.createTime }}</template>
                </el-table-column>
                <el-table-column label="更新时间" width="160">
                  <template slot-scope="scope">{{ scope.row.modifyTime }}</template>
                </el-table-column>
              </el-table>
              <el-table
                v-if="this.linkRef.actionType =='3'"
                :data="linkedArticle"
                border
                fit
                highlight-current-row
              >
                <el-table-column label="文章标题" min-width="250">
                  <template slot-scope="scope">{{ scope.row.title }}</template>
                </el-table-column>
                <el-table-column label="文章栏目">
                  <template slot-scope="scope">{{ scope.row.columnName }}</template>
                </el-table-column>
                <el-table-column label="是否发布" width="80">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.isRelease == '0'" type="success">是</el-tag>
                    <el-tag v-if="scope.row.isRelease == '1'" type="info">否</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="更新时间" width="160">
                  <template slot-scope="scope">{{ scope.row.modifyTime?scope.row.modifyTime:'暂无修改信息'}}</template>
                </el-table-column>
              </el-table>
              <el-table
                v-if="this.linkRef.actionType =='5'"
                :data="linkedHelp"
                border
                fit
                highlight-current-row
              >
                <el-table-column label="帮助标题">
                  <template slot-scope="scope">{{ scope.row.name }}</template>
                </el-table-column>
                <el-table-column label="帮助分类">
                  <template slot-scope="scope">{{ scope.row.cateName }}</template>
                </el-table-column>
                <el-table-column label="是否显示">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.isShow == '0'" type="success">是</el-tag>
                    <el-tag v-if="scope.row.isShow == '1'" type="info">否</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="排序">
                  <template slot-scope="scope">{{ scope.row.sort }}</template>
                </el-table-column>
              </el-table>
              <el-button class="filter-item" style="margin-top: 10px" type="primary" size="medium"
                         @click="clearChoosedLink">清除链接
              </el-button>
            </el-tab-pane>
            <!-- 商品 -->
            <el-tab-pane label="商品" name="1">
              <div class="filter-container">
                <el-input v-model="listQuery.name" maxlength="64" placeholder="商品名称" size="medium" style="width: 200px"
                          class="filter-item"/>
                <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search" @click="handleQuery">搜索
                </el-button>
              </div>
              <el-table
                style="font-size: 13px"
                v-loading="listLoading"
                :data="skus"
                border
                fit
                highlight-current-row
              >
                <el-table-column align="center" width="40">
                  <template slot-scope="scope">
                    <el-radio class="table_radio" :label="scope.row.id" @change.native="selectSku(scope.row)"
                              v-model="selectedId">&nbsp;
                    </el-radio>
                  </template>
                </el-table-column>
                <el-table-column label="图片" width="80">
                  <template slot-scope="scope"><img :src="scope.row.url" width="50" height="50"></template>
                </el-table-column>
                <el-table-column label="货品名称" min-width="250">
                  <template slot-scope="scope">{{ scope.row.name }}</template>
                </el-table-column>
                <el-table-column label="货品规格">
                  <template slot-scope="scope">
                    <span v-html="getSpecValues(scope.row.skuSpecValues)"></span>
                  </template>
                </el-table-column>
                <el-table-column label="销售价">
                  <template slot-scope="scope">{{ scope.row.price.toFixed(2) }}</template>
                </el-table-column>
                <el-table-column label="店铺名称">
                  <template slot-scope="scope">{{ scope.row.storeName }}</template>
                </el-table-column>
              </el-table>

              <div class="pagination-container">
                <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                               :current-page="listQuery.pageNum+1" :page-sizes="[10,20,30,50]"
                               :page-size="listQuery.pageSize"
                               layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
              </div>
            </el-tab-pane>
            <!-- 分类 -->
            <el-tab-pane label="分类" name="2">
              <el-tree
                v-model="selectCateId"
                style="max-height: 300px; overflow: auto; padding: 10px 0; border: 1px solid #DCDFE6; border-radius: 4px"
                @check="checkCate"
                :props="props"
                :load="loadNode"
                ref="tree"
                :check-strictly="true"
                :default-expand-all="true"
                lazy
                node-key="id"
                show-checkbox>
              </el-tree>
            </el-tab-pane>
            <!-- 文章 -->
            <el-tab-pane label="文章" name="3" style="min-height: 300px">
              <div class="filter-container">
                <el-input placeholder="文章标题" maxlength="32" v-model="articleListQuery.title" size="medium"
                          style="width: 200px;" class="filter-item"/>
                <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search"
                           @click="handleArticleFilter">搜索
                </el-button>
              </div>

              <el-table
                style="font-size: 13px"
                v-loading="articleListLoading"
                :data="articles"
                border
                fit
                highlight-current-row
              >
                <el-table-column align="center" width="40">
                  <template slot-scope="scope">
                    <el-radio class="table_radio" :label="scope.row.id" @change.native="selectArticle(scope.row)"
                              v-model="selectedHelpId">&nbsp;
                    </el-radio>
                  </template>
                </el-table-column>
                <el-table-column label="文章标题" min-width="250">
                  <template slot-scope="scope">{{ scope.row.title }}</template>
                </el-table-column>
                <el-table-column label="文章栏目">
                  <template slot-scope="scope">{{ scope.row.columnName }}</template>
                </el-table-column>
                <el-table-column label="是否发布" width="80">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.isRelease == '0'" type="success">是</el-tag>
                    <el-tag v-if="scope.row.isRelease == '1'" type="info">否</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="更新时间" width="160">
                  <template slot-scope="scope">{{ scope.row.modifyTime?scope.row.modifyTime:'暂无修改信息'}}</template>
                </el-table-column>
              </el-table>

              <div class="pagination-container">
                <el-pagination background @size-change="handleArticleSizeChange"
                               @current-change="handleArticleCurrentChange"
                               :current-page="articleListQuery.pageNum+1" :page-sizes="[10,20,30,50]"
                               :page-size="articleListQuery.pageSize"
                               layout="total, sizes, prev, pager, next, jumper" :total="articleTotal">
                </el-pagination>
              </div>


            </el-tab-pane>
            <!-- 专题 -->

            <!-- 帮助 -->
            <el-tab-pane label="帮助" name="5" style="min-height: 300px">
              <div class="filter-container">
                <el-input placeholder="帮助标题" maxlength="32" v-model="helpListQuery.name" size="medium" style="width: 200px;"
                          class="filter-item"/>
                <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search" @click="handleHelpFilter">
                  搜索
                </el-button>
              </div>

              <el-table
                style="font-size: 13px"
                v-loading="helpListLoading"
                :data="helps"
                border
                fit
                highlight-current-row
              >
                <el-table-column align="center" width="40">
                  <template slot-scope="scope">
                    <el-radio class="table_radio" :label="scope.row.id" @change.native="selectHelp(scope.row)"
                              v-model="selectedHelpId">&nbsp;
                    </el-radio>
                  </template>
                </el-table-column>
                <el-table-column label="帮助标题">
                  <template slot-scope="scope">{{ scope.row.name }}</template>
                </el-table-column>
                <el-table-column label="帮助分类">
                  <template slot-scope="scope">{{ scope.row.cateName }}</template>
                </el-table-column>
                <el-table-column label="是否显示">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.isShow == '0'" type="success">是</el-tag>
                    <el-tag v-if="scope.row.isShow == '1'" type="info">否</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="排序">
                  <template slot-scope="scope">{{ scope.row.sort }}</template>
                </el-table-column>
              </el-table>

              <div class="pagination-container">
                <el-pagination background @size-change="handleHelpSizeChange"
                               @current-change="handleHelpCurrentChange"
                               :current-page="helpListQuery.pageNum+1" :page-sizes="[10,20,30,50]"
                               :page-size="helpListQuery.pageSize"
                               layout="total, sizes, prev, pager, next, jumper" :total="helpTotal">
                </el-pagination>
              </div>
            </el-tab-pane>
            <!-- 功能链接 -->
            <el-tab-pane label="功能链接" name="6" style="min-height: 300px">
              <el-select class="filter-item" v-model="functionUrl" size="medium" style="width: 100%">
                <el-option label="未选择" value="-1"></el-option>
                <el-option label="领券中心" value="/pages/user/coupon/list"></el-option>
                <el-option label="红包中心" value="/pages/user/red/list"></el-option>
                <el-option label="秒杀" value="/pages/marketing/skillMarking"></el-option>
                <el-option label="预售中心" value="/pages/marketing/preMarking"></el-option>
                <el-option label="试用中心" value="/pages/marketing/tryMarking"></el-option>
                <el-option label="积分商城" value="/pages/product/jifenList"></el-option>
                <el-option label="拼团" value="/pages/marketing/groupMarking"></el-option>
                <el-option label="砍价" value="/pages/marketing/kanMarking"></el-option>
              </el-select>
            </el-tab-pane>
            <!-- 自定义 -->
            <el-tab-pane label="自定义" name="7" style="min-height: 300px">
              <el-input placeholder="请输入自定义链接地址" size="medium" style="width: 100%" class="filter-item"
                        v-model="customizeUrl"/>
            </el-tab-pane>
          </el-tabs>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogLinkVisible = false">取消</el-button>
            <el-button type="primary" @click="saveLink">确定</el-button>
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
   import {
      queryTemplateSkus,
      queryArticleList,
      queryThematic,
      queryHelpList,
      queryAllCategory,
      querySkuById,
      queryArticleById,
      queryThematicById,
      queryHelpById,
      queryTemplate,
      updateTemplate
    } from '@/api/templateone';
  // 功能链接映射关系
      const functionUrlMapping = {
        '1': '领券中心',
        '2': '红包中心',
        '3': '秒杀',
        '4': '预售中心',
        '5': '试用中心',
        '6': '积分商城',
        '7': '众筹项目'
      }
       import {Input} from "element-ui";
import {getToken} from '@/utils/auth'
  export default {
     components: {
          Input,

        },
    data() {
      const rateValidator = (rule, value, callback) => {
        if (!value || !/^100$|^(\d|[1-9]\d)$/.test(value)) {
          callback(new Error('请输入0-100之间的整数'));
        } else {
          callback();
        }
      }
      return {
      dialogLinkVisible: false, // 链接编辑显示隐藏标记

                      listQuery: {
                                pageNum: 0,
                                pageSize: 10,
                                name: '',
                                skuNo: '',
                              }, // 查询参数
                              skus: null, // 单品数据
                              total: null, // 数据总数,
                              tempSelectedSku: {},// 临时选中的sku信息
                              selectedId: '',// 选中的单品id
                              articleListQuery: {
                                pageNum: 0,
                                pageSize: 10,
                                title: '',
                              }, // 查询参数
                              articleListLoading: true, // 加载中
                              articles: null, // 文章数据
                              articleTotal: null, // 数据总数,
                              selectedArticleId: '',// 选中的文章id
                              tempSelectedArticle: {},// 临时选中的文章
                              helpListQuery: {
                                pageNum: 0,
                                pageSize: 10,
                                name: '',
                              }, // 查询参数
                              helpListLoading: true, // 加载中
                              helps: null, // 帮助数据
                              helpTotal: null, // 数据总数,
                              selectedHelpId: '',// 选中的帮助id
                              tempSelectedHelp: {},// 临时选中的帮助
                              selectCateId: 0,//选中的分类id
                              selectCateGrade:0,
                              allCates: [],// 所有的分类
                              firstCates: [],// 所有的一级分类
                               thematicListQuery: {
                                        pageNum: 0,
                                        pageSize: 10,
                                        name: '',
                                      }, // 查询参数
                                      thematicListLoading: true, // 加载中
                                      thematics: null, // 专题数据
                                      thematicTotal: null, // 专题数据总数,
                                      selectedThematicId: '',// 选中的专题id
                                      tempSelectedThematic: {},// 临时选中的专题
                                      customizeUrl: '',// 自定义链接地址
                                      linkRef: {
                                        actionType: '',
                                        actionParamId: ''
                                      },// 编辑链接的引用
                                      linkedSkus: [],// 链接选中的单品
                                      linkedThemtics: [],//链接选中的专题
                                      linkedCate: {},// 链接选中的分类
                                      linkedHelp: [], // 链接选中的帮助
                                      linkedArticle: [],// 链接选中的文章
                                      linkedFunction: '',// 链接选中的功能链接
                                      functionUrl: '-1', // 当前选中的功能链接
                                      props: {
                                        label: 'name',
                                        children: 'zones',
                                        isLeaf: 'leaf'
                                      },
                                       tabActiveName: '0',// 当前选中的tab
                                       listLoading:false,
        typeDisabled: false,// 类型选择框是否可以选择
        DiaType: 1, //  弹窗类型 1 新增 2 修改
        choosedFid: '', // 当前选择的一级分类
        choosedSid: '', // 当前选择的二级分类
        form: {name: '', sort: '', grade: '1',modifyName:'',delName:''}, // 一级分类
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
         headers: { // 设置上传的请求头部
                        Authorization: 'Bearer ' + getToken()
                      },
               uploadApi: process.env.VUE_APP_BASE_API+'/aliyun/oss/uploadToAliOss',// 上传文件路由
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
       this.queryAllCates();
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
       uploadMobilePanicPicSuccess(res) {
                  this.form.modifyName = res;
                },
                // 上传PC预售广告图回调
                uploadPcPreSalePicSuccess(res) {
                  this.form.modifyName = res;
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
          this.form = {id: category.id, name: category.name, sort: category.sort, grade: category.grade,delName: category.delName,modifyName: category.modifyName};
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
       // 编辑链接
                toEditLink(link) {
                this.clearLink();
                  this.tabActiveName = '0';
                  this.dialogLinkVisible = true
                  this.linkedSkus = []
                          this.linkedThemtics = []
                          this.linkedCate = {}
                          this.linkedHelp = []
                          this.linkedArticle = []
                          this.linkedFunction = ''
                },
                  // 清除选中的链接
                      clearLink() {
                        this.tempSelectedSku = {};
                        this.selectedId = '';
                        this.selectCateId = 0;
                        this.tempSelectedThematic = {};
                        this.selectedThematicId = '';
                        this.selectedArticleId = '';
                        this.tempSelectedArticle = {};
                        this.selectedHelpId = '';
                        this.tempSelectedHelp = {};
                        this.functionUrl = '-1';
                        this.customizeUrl = '';
                      },
                      // 保存链接
                      saveLink() {

                        // 选中的是自定义链接，不能为空
                        if (this.tabActiveName == '7' && !this.customizeUrl) {
                          this.showError('自定义链接不能为空！');
                          return;
                        }
                        // 当前选中的是商品
                        if (this.selectedId != '') {
                          this.linkRef.actionType = '1';
                          this.linkRef.actionParamId = this.selectedId;
                          this.form.delName='/pages/product/product?id='+this.tempSelectedSku.spuId
                        } else if (this.selectCateId != 0) {
                          // 当前选中的是分类
                          this.linkRef.actionType = '2';
                          this.linkRef.actionParamId = this.selectCateId;
                          if(this.selectCateGrade==1){
                            this.form.delName='/pages/product/list?firstCateId='+this.selectCateId
                          }else  if(this.selectCateGrade==2){
                                                      this.form.delName='/pages/product/list?secondCateId='+this.selectCateId
                                                    }else  if(this.selectCateGrade==3){
                                                                                this.form.delName='/pages/product/list?thirdCateId='+this.selectCateId
                                                                              }

                        } else if (this.selectedArticleId != '') {
                          // 当前选中的是文章
                          this.linkRef.actionType = '3';
                          this.linkRef.actionParamId = this.selectedArticleId;
                           this.form.delName='/pages/set/article/detail?id='+this.selectedArticleId
                        } else if (this.selectedThematicId != '') {
                          // 当前选中的是专题
                          this.linkRef.actionType = '4';
                          this.linkRef.actionParamId = this.selectedThematicId;
                        } else if (this.selectedHelpId != '') {
                          // 当前选中的是帮助
                          this.linkRef.actionType = '5';
                          this.linkRef.actionParamId = this.selectedHelpId;
                          this.form.delName='/pages/set/helper/detail?id='+this.selectedHelpId
                        } else if (this.functionUrl != '-1') {
                          // 选择的是功能链接
                          this.linkRef.actionType = '6';
                          this.linkRef.actionParamId = this.functionUrl;
                           this.form.delName=this.functionUrl;
                        } else if (this.customizeUrl != '') {
                          // 选择的是自定义链接
                          this.linkRef.actionType = '7';
                          this.linkRef.actionParamId = this.customizeUrl;
                           this.form.delName=this.customizeUrl;
                        }
                      console.log(this.linkRef)

                        this.dialogLinkVisible = false
                        this.$refs.tree.setCheckedKeys([]);
                      },
                      // 清除已经选中的链接
                      clearChoosedLink() {
                        this.linkRef.actionType = '';
                        this.linkRef.actionParamId = '';
                      },

            toLink(link) {
              this.$router.push({path: link})
            },
            // 改变tab
            changeTab() {
              // 选中的是商品查询商品信息
              if (this.tabActiveName == '1') {
                this.querySkus();
              } else  if (this.tabActiveName == '2') {

                             }else if (this.tabActiveName == '3') {
                //选中的是文章查询
                this.queryArticleList();
              } else if (this.tabActiveName == '4') {
                // 专题查询
                this.queryThematicList();
              } else if (this.tabActiveName == '5') {
                // 帮助查询
                this.queryHelpList();
              }
            },
            // 查询单品
            querySkus() {
              this.listLoading = true
              queryTemplateSkus(this.listQuery).then(response => {
                this.skus = response.data;
                this.total = response.recordsTotal;
                this.listLoading = false
              });
            },
            // 选中单品
            selectSku(sku) {
              this.selectedId = sku.id;
              this.tempSelectedSku = sku;
            },
            // 过滤查询
            handleQuery() {
              this.listQuery.pageNum = 0;
              this.querySkus();
            },
            handleSizeChange(val) {
              this.listQuery.pageNum = 0;
              this.listQuery.pageSize = val;
              this.querySkus();
            },
            handleCurrentChange(val) {
              this.listQuery.pageNum = val - 1;
              this.querySkus();
            },
            // 查询文章
            queryArticleList() {
              this.articleListLoading = true
              queryArticleList(this.articleListQuery).then(response => {
                this.articles = response.data;
                this.articleTotal = response.recordsTotal;
                this.articleListLoading = false
              });
            },
            // 过滤查询
            handleArticleFilter() {
              this.articleListQuery.pageNum = 0;
              this.queryArticleList();
            },
            handleArticleSizeChange(val) {
              this.articleListQuery.pageNum = 0;
              this.articleListQuery.pageSize = val;
              this.queryArticleList();
            },
            handleArticleCurrentChange(val) {
              this.articleListQuery.pageNum = val - 1;
              this.queryArticleList();
            },
            // 选中文章
            selectArticle(article) {
              this.selectedArticleId = article.id;
              this.tempSelectedArticle = article;
            },
            // 查询帮助
            queryHelpList() {
              this.helpListLoading = true
              queryHelpList(this.helpListQuery).then(response => {
                this.helps = response.data;
                this.helpTotal = response.recordsTotal;
                this.helpListLoading = false
              });
            },
            // 过滤查询
            handleHelpFilter() {
              this.helpListQuery.pageNum = 0;
              this.queryHelpList();
            },
            handleHelpSizeChange(val) {
              this.helpListQuery.pageNum = 0;
              this.helpListQuery.pageSize = val;
              this.queryHelpList();
            },
            handleHelpCurrentChange(val) {
              this.helpListQuery.pageNum = val - 1;
              this.queryHelpList();
            },
            // 选中帮助
            selectHelp(help) {
              this.selectedHelpId = help.id;
              this.tempSelectedHelp = help;
            },

            // 查询专题
            queryThematicList() {
              this.thematicListLoading = true
              queryTemplateThematicList(this.thematicListQuery).then(response => {
                this.thematics = response.data;
                this.thematicTotal = response.recordsTotal;
                this.thematicListLoading = false
              });
            },
            // 过滤查询
            handleThematicFilter() {
              this.thematicListQuery.pageNum = 0;
              this.queryThematicList();
            },
            handleThematicSizeChange(val) {
              this.thematicListQuery.pageNum = 0;
              this.thematicListQuery.pageSize = val;
              this.queryThematicList();
            },
            handleThematicCurrentChange(val) {
              this.thematicListQuery.pageNum = val - 1;
              this.queryThematicList();
            },
            // 选中专题
            selectThematic(thematic) {
              this.selectedThematicId = thematic.id;
              this.tempSelectedThematic = thematic;
            },

            //选择boss分类
            checkCate(even) {
              this.$refs.tree.setCheckedKeys([]);
              this.$refs.tree.setChecked(even.id, true, false)

              this.selectCateId = even.id;
              this.selectCateGrade = even.grade;
            },
            //加载 el-tree 数据
            loadNode(node, resolve) {

              if (node.level === 0) {
                return resolve(this.firstCates);
              }
              if (node.level == 1 || node.level == 2) {
                let second = new Array();
                for (let i = 0; i < this.allCates.length; i++) {
                  if (this.allCates[i].parentId == node.data.id) {
                    let s = {
                      id: this.allCates[i].id,
                      name: this.allCates[i].name,
                      grade: this.allCates[i].grade,
                    }
                    second.push(s);
                  }
                }
                return resolve(second);
              }
              if (node.level == 3) {
                return resolve([]);
              }
            },
            // 查询所有分类
            queryAllCates() {
              queryAllCategory().then(res => {
                if (res) {
                  this.allCates = res;
                  for (let i = 0; i < res.length; i++) {
                    if (res[i].grade == 1) {
                      this.firstCates.push({
                        id: res[i].id,
                        name: res[i].name,
                        grade: this.allCates[i].grade,
                      });
                    }
                  }
                }
              })
            },
    },
        computed: {
                            getSpecValues() {
                              return function (skuSpecValues) {
                                let content = '';

                                for (let i = 0; i < skuSpecValues.length; i++) {
                                  content += skuSpecValues[i].spec.name + ":" + skuSpecValues[i].valueRemark + "<br>";
                                }
                                return content;
                              }
                            }
                          },
  }
</script>
