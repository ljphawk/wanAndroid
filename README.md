#### Module说明：

1. 	app：主工程
2. 	lib_base：基类模板，封装BaseActivity，BaseFragment，BaseLayout等，提供基类让主app和其他Module进行依赖使用
3. 	lib_utils：提供常用的工具类，有需要的Module可以进行依赖
4. 	lib_dialog：封装了弹窗，具体使用案例请查看app中dialog包内的类代
5. 	lib_respository：数据仓库，封装了网络请求，及mmkv数据存储
> 注：所有lib_*，app都已依赖
#### Activity，Fragment，Adapter，Layout等编写
> ActivityMainBinding为MainActivity的布局文件名字activity_main，as自动编译生成ActivityMainBinding文件
```
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
             binding.tvContent.text = "hello word"
    }
}
```
> HomeFragment同理MainActivity
```
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>() {
    
    override fun initData(view: View, savedInstanceState: Bundle?) {
             binding.tvContent.text = "hello word"
    }
}
```
> 自定义ViewGroup类
```
class LoginLayout(context: Context, attributeSet: AttributeSet) :
    BaseBindingLayout<LayoutLoginView>(context, attributeSet) {
    
    override fun attrId(): IntArray? {
        TODO("Not yet implemented")
    }

    override fun initData(typedArray: TypedArray?) {
         binding.tvContent.text = "hello word"
    }
}
```
> Recyclerview Adapter
```
class NewsAdapter : BaseBindingQuickAdapter<ItemBean, ItemNewsBinding>() {

    override fun convert(holder: BaseBindingHolder, item: ItemBean) {
      holder.getViewBinding<ItemNewsBinding>().apply{
          tvContent.text = "hello word"
      }
    }
}
```
#### 网络请求

1. 创建class 继承BaseApiResponse<T> ，重写errorCode，errorMessage，data，isSuccessful等方法，isSuccessful返回成功条件，服务器返回的外层字段，自行随意添加，示例：
```
class TestApiResponse<T> : BaseApiResponse<T>() {

    private val data: T? = null
    private val errorCode: Int? = null
    private val errorMsg: String? = null

    override fun errorCode(): Int? {
        return errorCode
    }

    override fun errorMessage(): String? {
        return errorMsg
    }

    override fun data(): T? {
        return data
    }

    override fun isSuccessful(): Boolean {
        return errorCode == 0
    }

}
```
2.创建 **ApiService接口，示例代码如下：
```
interface TestApiService {

    @FormUrlEncoded
    @POST("https://wanandroid.com/user/login")
    suspend fun login("参数省略"): TestApiResponse<User>
    
    @POST("https://wanandroid.com/wxarticle/chapters/json")
    suspend fun getWxarticleList(): TestApiResponse<MutableList<WxArticleBean>>
    
}

```
3.创建 **Repository类并继承BaseRepository
```
class TestRepository : BaseRepository() {

    private val testApiService by lazy {
        RetrofitClient.getApiService(TestApiService::class.java)
    }

    suspend fun login(name: String, pwd: String) = executeHttp { testApiService.login(name, pwd) }


    suspend fun getWxarticleList() = executeHttp(testApiService::getWxarticleList)

    suspend fun getNetDataError() = executeHttp(testApiService::getNetDataError)
}
```
RetrofitClient实现如下，继承于BaseRetrofitClient,getApiService方法可传其他host url
```
object RetrofitClient : BaseRetrofitClient() {

    /**
     * 添加需要的拦截器
     */
    override fun handleBuilder(builder: OkHttpClient.Builder) {
        builder.addInterceptor(HeaderInterceptor())
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }
    }

    override fun baseUrl(): String {
        return EnvConstant.getApiHost()
    }
}
```
4.创建 **ViewModel
```
class TestViewModel : BaseViewModel() {

    private val testRepository = TestRepository()

    private val _uiState = MutableStateFlow<BaseApiResponse<MutableList<WxArticleBean>>>(TestApiResponse())
    var uiState = _uiState.asStateFlow()

    suspend fun getWxarticleList() {
        _uiState.value = testRepository.getWxarticleList()
    }

    suspend fun login(name: String, pwd: String) = testRepository.login(name, pwd)

```
请求示例1，launchAndCollect扩展方法可传其他参数（showLoading，showErrorToast）
```
class TestActivity : BaseBindingActivity<ActivityTestBinding>() {

    private val testVm by viewModels<TestViewModel>()

    override fun initData(savedInstanceState: Bundle?) {
        binding.btLogin.noQuickClick {
            login()
        }
    }

    private fun login() {
        launchAndCollect({ testVm.login("FastJetpack", "FastJetpack") }) {
            onSuccess = {
                binding.tvContent.text = it.toString()
            }
            onFailed = { _, errorMsg ->
                binding.tvContent.text = errorMsg
            }
        }
    }

```
请求示例2，使用场景:Fragment和Activity的ViewModel是一个，可同时监听一个接口的数据变化,launchAndCollectIn扩展方法可传其他参数（showLoading，showErrorToast）
```
class TestActivity : BaseBindingActivity<ActivityTestBinding>() {

    private val testVm by viewModels<TestViewModel>()

    override fun initData(savedInstanceState: Bundle?) {
        //发起请求
        binding.btGet.noQuickClick {
            launch { testVm.getWxarticleList() }
        }
        //监听请求结果变化
        testVm.uiState.launchAndCollectIn(this) {
            onSuccess = {
                binding.tvContent.text = it.toString()
            }
            onFailed = { _, msg ->
                binding.tvContent.text = msg
            }
        }
    }
}

```
#### MMKV使用示例：
lib_respository提供了BasePreference抽象类，根据具体业务需求可在app的preference包中创建自定义单例类继承BasePreference类，具体实现如下:
```
object UserPreference : BasePreference() {
    
    override fun create(): MMKV {
        return MMKV.mmkvWithID("user")!!
    }

    var userId by stringValue()

    var token by stringValue()

    fun logout() {
        clearAll()
    }
    
}

设置数据: UserPreference.userId = "userId"
获取数据: textView.setText(UserPreference.userId)
BasePreference提供了stringValue、booleanValue、intValue、parcelableValue，等九种数据类型存储方法，以及单个数据的remove，全部数据的clear;
```