# Fitnessch
Fitnessch 是一個記錄健身課表與飲食菜單的 APP，不同以往自己記錄自己的計畫，Fitnessch 提供一個平台讓使用者能夠互相分享，藉由這個平台能夠看到其他使用者的進程，並以此來改進自己的課表與菜單。

歡迎喜愛健身或是正想開始健身的朋友們下載下來開始管理且分享自己的計劃吧！


[<img src="https://github.com/steverichey/google-play-badge-svg/blob/master/img/en_get.svg" width="25%" height="25%">](https://play.google.com/store/apps/details?id=com.kevinj1008.fitnessch)

# Features
* [登入頁面]

  * 使用 Google 帳號登入

* [主頁面]

  * 按照時間排序所有使用者所發的文章
  * 使用不同標籤圖案分開顯示課表與菜單
  * 點擊文章卡片可進入內文頁
  * 點擊作者頭像可進入作者個人檔案
  * 即時顯示最新文章
  
* [歷史課表]

  * 運用月曆來顯示使用者該日期是否有新增過文章
  * 點擊日期可進入單日文章頁
  * 左右滑動可更換月份，並顯示當月份有新增文章的日期
  * 未建立文章的月份，點擊任一日期會自動跳轉到當前月份
  
* [單日課表與菜單頁]

  * 使用不同標籤圖案分開顯示課表與菜單
  * 菜單與課表分層顯示
  * 日期自動變更
  * 點擊文章可進入內文頁
  
* [內文頁面]

  * 分層顯示文章標題、內容與課表或菜單
  * 點擊作者圖像可進入作者個人檔案
  
* [個人檔案]

  * 使用 Tab 分開顯示課表與菜單
  * 可編輯個人身高、體重與資訊
  * 即時連動 Firebase 資料庫
  * 點擊文章卡片可進入內文頁
  
* [其他使用者檔案]

  * 使用 Tab 分開顯示課表與菜單
  * 即時連動 Firebase 資料庫
  * 點擊文章卡片可進入內文頁
  * Navigation Drawer 變更為倒退鍵，直到退回主頁面
  
* [新增課表與菜單頁]

  * 使用 Tab 分開顯示新增課表與新增菜單
  * 限制項目與菜單只能輸入英文與中文
  * 限制重量、次數與卡路里只能輸入數字
  * 限制重量與次數不超過三位數
  * 限制空白輸入
  * 提供課表項目與菜單關鍵字搜尋
  * 新增課表頁提供計時器，讓使用者在訓練中可查看自己一組花費多少時間
  * 計時器可隨時暫停和長按歸零
  * 新增的項目或菜單會依照同標題去排序
  * 長按新增之課表或菜單可刪除
  
* [新增文章頁]

  * 文章標題限制 15 字
  * 限制空白輸入
  * 即時新增到 Firebase 資料庫
  
* [Navigation Drawer]

  * 點擊 Drawer Toggle 可開啟側滑選單切換相關頁面或是「登出」
  
# Screenshot

<img src="https://github.com/KevinJ1008/Fitnessch/blob/master/screenshots/fitnessch_screenshot_04.PNG" width="210"> <img src="https://github.com/KevinJ1008/Fitnessch/blob/master/screenshots/fitnessch_screenshot_01.PNG" width="212"> <img src="https://github.com/KevinJ1008/Fitnessch/blob/master/screenshots/fitnessch_screenshot_02.PNG" width="213">

<img src="https://github.com/KevinJ1008/Fitnessch/blob/master/screenshots/fitnessch_screenshot_03.PNG" width="210"> <img src="https://github.com/KevinJ1008/Fitnessch/blob/master/screenshots/fitnessch_screenshot_05.PNG" width="210"> <img src="https://github.com/KevinJ1008/Fitnessch/blob/master/screenshots/fitnessch_screenshot_06.PNG" width="213" height="372">

# Implemented

* Design Patterns

  * Object-Oriented Programming
  * Model-View-Presenter (MVP)

* Core Functions

  * Google Login (Firebase Authentication)
  * Key Word Search
  * Calendar View Query
  * Cloud Firestore API
  
* User Interface

  * Fragment + Animation
  * RecyclerView
  * ViewPager
  * TabLayout
  * SwipeRefreshLayout
  * FloatingActionButton
  * MaterialCalendarView (Third-party Liabrary)
  * DrawerToggle
  * Picasso (Third-party Library)
  
* Storage

  * Firebase Cloud Firestore
  * SharedPreference
  
* Analysis

  * Crashlytics
  
* Unit Test

  * JUnit
  * Mockito
  * Espresso
  
* Other

  * NetworkChecker - When Network state change, send Boradcast, and determine to use App or not
  * Activity recycle issue - When Activity recycle by system, restart Application to avoid crash issue
  * Firebase Remote Config - Use Remote Config to force update in the future
  
# Requirement

* Android Studio 3.0+
* Android SDK 23+
* Gradle 3.1.3+

# Version

* 1.0.3 - 2019/03/26

  * Hot Fix - 修正程式閃退問題。
  * 優化未有網路連線時提示使用者。
  * 新增版本判斷，將來可執行強更。

* 1.0.2 - 2018/10/31
  
  * 新增內文頁進入其他使用者檔案功能。
  * 調整內文頁字體大小。
  * 修正內文頁未顯示預設作者圖的問題。
  
* 1.0.1 - 2018/10/30

  * 新增從主頁進入其他使用者檔案頁面功能。
  * 修正大解析度螢幕顯示錯誤問題。
  
* 1.0.0 - 2018/10/26

  * 發布正式版。
  * 修正內文頁顯示錯誤問題。
  * 調整細部 UI 呈現。
  
* 0.0.7 - 2018/10/21

  * 增加頁面轉換的動畫效果。
  * 修復發文無法新增課表及菜單。
  
* 0.0.6 - 2018/10/19

  * 新增建置課表與菜單時提供關鍵字搜尋。
  * 其他使用者頁面建置中。
  
* 0.0.5 - 2018/10/18

  * 更改部分頁面 UI 呈現。
  * 修正內文頁快速跳轉時會閃退之問題。
  * 新增在增加課表或菜單頁時能夠讓使用者刪除項目之功能。
  * 優化頁面轉換後部分清單呈現之問題。
  
* 0.0.4 - 2018/10/17

  * Hot Fix - 修正卡登入權限錯誤。
  
* 0.0.2 - 2018/10/16

  * 更改登入流程設定。
  * 變更內文版面配置。
  
* 0.0.1 - 2018/10/16

  * 提供新增課表及菜單發文功能。
  * 個人檔案基本編輯及分類搜尋歷史文章。
  * 月曆搜尋歷史課表，併用顏色區分資料有無。
  * 主頁瀏覽全使用者的文章。
  
# Contact

Wun-Bin Jhou  
wbjstudio@gmail.com
  
