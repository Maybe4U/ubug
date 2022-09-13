# UBug
UBug主要功能是将应用crash日志保存到本地以定位应用崩溃原因。
##UBug的使用
### Step.1
`implementation 'io.github.maybe4u:ubug:0.0.1'`
### Step.2
在自己的`Application`中进行初始化。

kotlin写法

`UBug.instance.init(this)`  
### Step.3
测试，调用`bugTest()`方法生成一个bug，生成的bug文件保存到本地`/storage/emulated/0/Android/data/[packageName]/files/Crash`下，以时间戳命名。
平板通过“设置-应用-文件”查看，自带文件管理器暂时不能访问该目录。
查看文件中记录如下内容。
```
java.lang.ArithmeticException: divide by zero
        at com.xk.tablet.ubug.CrashFileUtilKt.bugTest(CrashFileUtil.kt:19)
        at com.xk.tablet.crashpushdemo.MainActivity.onCreate$lambda-0(MainActivity.kt:15)
        at com.xk.tablet.crashpushdemo.MainActivity.$r8$lambda$bREjppNJyXcWTZTviIHSOWrPEu8(Unknown Source:0)
        at com.xk.tablet.crashpushdemo.MainActivity$$ExternalSyntheticLambda0.onClick(Unknown Source:0)
        at android.view.View.performClick(View.java:7455)
        at com.google.android.material.button.MaterialButton.performClick(MaterialButton.java:1119)
        at android.view.View.performClickInternal(View.java:7432)
        at android.view.View.access$3700(View.java:835)
        at android.view.View$PerformClick.run(View.java:28810)
        at android.os.Handler.handleCallback(Handler.java:938)
        at android.os.Handler.dispatchMessage(Handler.java:99)
        at android.os.Looper.loopOnce(Looper.java:201)
        at android.os.Looper.loop(Looper.java:288)
        at android.app.ActivityThread.main(ActivityThread.java:7870)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:548)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1003)
```
