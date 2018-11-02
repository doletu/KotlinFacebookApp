# 【Android】アプリにFacebookログイン機能をつけよう！for Kotlin
![画像1](/readme-img/Screen1.png)

## 概要
* [ニフクラ mobile backend](https://mbaas.nifcloud.com/)の『SNS連携（Facebook連携）』を利用して、Facebookのログイン機能を実装したサンプルプロジェクトです。
* Facebook連携で取得した会員データは[ニフクラ mobile backend](https://mbaas.nifcloud.com/)の『会員管理機能』として扱うことができます。
 * [【Android】アプリのログイン機能](https://github.com/NIFCloud-mbaas/KotlinLoginApp)も用意していますので、参照ください。
* このコンテンツは簡単な操作ですぐに [ニフクラ mobile backend](https://mbaas.nifcloud.com/)の機能を体験いただけます。

## ニフクラ mobile backendについて
スマートフォンアプリのバックエンド機能（プッシュ通知・データストア・会員管理・ファイルストア・SNS連携・位置情報検索・スクリプト）が**開発不要**、しかも基本**無料**(注1)で使えるクラウドサービス！今回はデータストアを体験します。

注1：詳しくは[こちら](https://mbaas.nifcloud.com/price.htm)をご覧ください。

![画像2](/readme-img/Screen2.png)

## 動作環境
* Windows 7 Professional
* Facebook SDK 4.12.1
* Android Studio 1.5
* Android ver 4x,5x
* Android SDK v3

※上記内容で動作確認をしています。<br>
※古いバージョンだと動作しない可能性があります。


## 手順
### 1. [ニフクラ mobile backend](https://mbaas.nifcloud.com/signup.htm)の会員登録とアプリ作成

* 上記リンクから会員登録（無料）をします。登録ができたらログインをすると下図のように「アプリの新規作成」画面が出るのでアプリを作成します。

![画像3](/readme-img/Screen3.png)

* アプリ作成されると下図のような画面になります。
* この２種類のAPIキー（アプリケーションキーとクライアントキー）はAndroidアプリに[ニフクラ mobile backend](https://mbaas.nifcloud.com/)を紐付けるために使用します。

![画像4](/readme-img/Screen4.png)

* 動作確認後に会員情報（アクセストークン）が保存される場所も確認しておきましょう。

![画像5](/readme-img/Screen5.png)

### 2. [GitHub](https://github.com/NIFCLOUD-mbaas/KotlinFacebookApp.git)からサンプルプロジェクトのダウンロード

* プロジェクトの[Githubページ](https://github.com/NIFCLOUD-mbaas/KotlinFacebookApp.git)から「Clone or download」＞「Download ZIP」をクリックします。
* プロジェクトを解凍します。

### 3. AndroidStudioでアプリを起動

* AndroidStudioを開き、解凍したプロジェクトを選択します。

![画像6](/readme-img/Screen6.png)

### 4. APIキーの設定

* AndroidStudioで `MainActivity.kt` を開きます。
   * ディレクトリはデフォルトで「Android」が選択されていますので、「Project」に切り替えてから探してください。
![画像8](/readme-img/project.png)

* 先程[ニフクラ mobile backend](https://mbaas.nifcloud.com/)のダッシュボード上で確認したAPIキーを貼り付けます。

![画像07](/readme-img/Screen7.png)

* それぞれ`YOUR_APPLICATION_KEY`と`YOUR_CLIENT_KEY`の部分を書き換えます。
  * このとき、ダブルクォーテーション（`"`）を消さないように注意してください！


### 5. FacebookSDKを使用するための準備と設定

* [facebook for developers](https://developers.facebook.com/)にログイン（Facebookアカウントがない場合はアカウントを作成）して、右上の「マイアプリ」からの「新しいアプリを追加」を選択します。

![画像10](/readme-img/Screen10.png)

* Androidを選択し、アプリ名（任意）を入力し、「新しいFacebookアプリIDを作成」をクリックします。
* 「連絡先メールアドレス」と「カテゴリ」を入力して「アプリIDを作成」をクリックしてFacebookアプリを作成します。
* Securityチェック欄には質問の対象画像を全て選択し、「確定」をクリックします。

![画像11](/readme-img/Screen11.png)

* 「Quick Start for Android」の画面が出ます。
* 一番右上の「Skip Quick Start」をクリックします。

![画像12](/readme-img/Screen12.png)

* ダッシュボードが表示されます。
  * `アプリID`を記憶します。

![画像13](/readme-img/Screen13.png)

* `アプリID`の設定します。
  * `/app/src/main/res/values/strings.xml`を編集します。

![画像8](/readme-img/Screen8.png)

* またこの`アプリID`は[ニフクラ mobile backend](https://mbaas.nifcloud.com/)にも設定します。
   * 左側のメニューから、「アプリ設定」を開き、SNS連携の設定を開きます。
   * 連携の許可にチェックを入れた上で、Facebookの開発者向けサイトで発行されたアプリIDを入力してください。
   * 「保存する」をクリックします。

![画像9](/readme-img/Screen9.png)

* 最後に「アプリレビュー」をクリックし、「***アプリ名***を公開しますか？」を「***はい***」にしておきます。

![画像14](/readme-img/Screen14.png)

* アプリをFacebookに結合する。
  * 「Add Platform」をクリックしてAndroidのアプリを追加選択します。
  * 「Key Hashes」欄に下記処理から値を取得・設定します。
  * （Windowsの場合）コマンドプロンプトから次のコマンドを入力します。パスワードを聞かれますので 'android' と返します。
  * このとき、opensslコマンドは「openssl場所」/bin/openssl.exeを指定しております。
  * １行目でコマンドを、２行目でパスワードを入力し、３行目でKey Hashが表示されます。

```
>keytool -exportcert -alias androiddebugkey -keystore %HOMEPATH%\.android\debug.keystore | openssl sha1 -binary | openssl base64
>キーストアのパスワードを入力してください: android
>0QYXA5jGgnGg/wco7iNiEXYdeVw=
```

![画像15](/readme-img/Screen15.png)

* アプリをFacebookに結合設定が無い場合はログインエラーになります。

![画像LoginError](/readme-img/LoginError.png)


### 6. 動作確認

* AndroidStudioからビルドする。
    * 「プロジェクト場所」\app\build\outputs\apk\ ***.apk ファイルが生成される。
* apk ファイルをインストールしてシミュレーターが起動したら、Login画面が表示されます。
* __Login__ ボタンをクリックします。
* Facebookログインの画面（ブラウザ）が表示されるので、必要事項を入力し、ログインを行います。
* ログインに成功したらログアウトします。

![画像16](/readme-img/Screen16.PNG)

-----

* 保存に成功したら、[ニフクラ mobile backend](https://mbaas.nifcloud.com/)のダッシュボードから「会員管理」を確認してみましょう！
* `authData`にはアクセストークンが保存されます。

![画像17](/readme-img/Screen17.png)

* [Facebook](https://www.facebook.com/)にログインして「アプリ」＞「アプリ設定」欄にログインの記録が保存されます。

![画像18](/readme-img/Screen18.png)


操作はここまでです。

## 解説
サンプルプロジェクトに実装済みの内容のご紹介

#### SDKのインポートと初期設定
* ニフクラ mobile backend の[Android ドキュメント（クイックスタート）](https://mbaas.nifcloud.com/doc/current/introduction/quickstart_android.html#/Android/)に開発中のアプリを連携する手順ご用意していますので、ご活用ください。

* [Android用Facebookログイン](https://developers.facebook.com/docs/facebook-login/android)と合わせてご活用ください。

#### ロジック
* FacebookSDKを使用するための処理を`MainActivity.kt`に記述しています

```kotlin

import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.nifcloud.mbaas.core.NCMB
import com.nifcloud.mbaas.core.NCMBException
import com.nifcloud.mbaas.core.NCMBFacebookParameters
import com.nifcloud.mbaas.core.NCMBUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //**************** APIキーの設定とSDKの初期化 **********************
        NCMB.initialize(this, "YOUR_APPLICATION_KEY", "YOUR_CLIENT_KEY");

        setContentView(R.layout.activity_main)

        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {

                        //Login to NIFCLOUD mobile backend
                        val parameters = NCMBFacebookParameters(
                                loginResult.accessToken.userId,
                                loginResult.accessToken.token,
                                loginResult.accessToken.expires
                        )
                        try {
                            NCMBUser.loginWith(parameters)
                            Toast.makeText(applicationContext, "Login to NIFCLOUD mbaas with Facebook account", Toast.LENGTH_LONG).show()
                        } catch (e: NCMBException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onCancel() {
                        // App code
                        Log.d("tag", "onCancel")
                    }

                    override fun onError(exception: FacebookException) {
                        // App code
                        Log.d("tag", "onError:$exception")
                    }
                })
    }
```

* Facebook連携はブラウザのFacebookページに遷移し、行われます。
* Facebookログイン、ログアウトボタン押下時の処理は以下の`com.facebook.login.LoginManager.kt`に記述しています

```kotlin

    // ログインボタン押下時の処理
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
```

## 参考
### FacebookSDKを使ってFacebook会員認証するのとニフクラ mobile backendSDKを使ってFacebook会員認証するのは何が違うのか？

![画像19](/readme-img/Screen19.png)

* もちろん直接FacebookSDKを呼ぶことも可能ですが、ニフクラ mobile backendSDKを呼べば裏でFacebookSDKを呼んで処理するので、１つ呼べば、Facebookへのログインとニフクラ mobile backendへ会員情報保存が同時に行えるので一石二鳥というわけです。
* また一度会員登録してしまえば、あとはニフクラ mobile backendの会員管理機能で処理が行えるので自前で会員管理システムを構築する必要がなくより楽に開発を行えます。

### もっと深く知りたい方へ
* ニフクラ mobile backend の[ドキュメント（SNS連携：Facebook連携）](https://mbaas.nifcloud.com/doc/current/sns/facebook_android.html#/Android/)をご用意していますので、ご活用ください。
* Facebook開発の [ドキュメント(Android用Facebookログイン)](https://developers.facebook.com/docs/facebook-login/android)参照ください。
