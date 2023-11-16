# Wicket Spring-Boot Practice

本プロジェクトは、千歳科学技術大学のプロジェクトメンバー用の講習資料である。

研究で独自開発するシステムのベースに使うJava Webフレームワークの組み合わせの中で、ユーザ作成機能、ログイン機能の実装を体験的に練習する。

これにより、クラシックなサーバサイドWebアプリケーションの作り方を学ぶ。

## 動作確認環境

- Java 21
- [Wicket-Spring-Boot](https://github.com/MarcGiffing/wicket-spring-boot) 4.0.0
    - Wicket 10
    - Spring Boot 3.1
- H2DB（PostgreSQL互換モード）
- 他
- macOS 12.7
- IntelliJ IDEA 2023

**注意事項**

- 学内開発ではPostgreSQLをデータベースに使うことが多いが、この講習では環境設定の簡単さを重視してH2DBのPostgreSQLモードを使う。

## 目次

### フレームワークの説明と練習の準備

1. [Apache Wicket](doc/A01/01.md)
2. [SpringとSpring Boot](doc/A02/01.md)
3. [開発練習の準備](doc/A03/01.md)

### 動作確認

1. WebPageを表示する
   - [01](doc/B01/01.md)
   - [02](doc/B01/02.md)
   - [課題](doc/B01/03.md)
2. WebPageに表示するものをSpringで作る
   - [01](doc/B02/01.md)
   - [課題](doc/B02/02.md)
3. WebPage間を移動する
   - [01](doc/B03/01.md)
   - [課題](doc/B03/02.md)

### ユーザ追加

1. ユーザ追加フォームを作成する
   - [01](doc/C01/01.md)
   - [02](doc/C01/02.md)
   - [03](doc/C01/03.md)
2. データベースとテーブルの準備
   - [01](doc/C02/01.md)
3. テーブルにユーザ情報を記録する
   - [01](doc/C03/01.md)
   - [課題](doc/C03/02.md)

### 基本的なユーザ認証

1. データのバリデーションを設定する
   - [01](doc/D01/01.md)
   - [課題](doc/D01/02.md)
2. ユーザ認証の仕組みを入れる（Wicket-Auth-Roles）
   - [01](doc/D02/01.md)
   - [02](doc/D02/02.md)
   - [03](doc/D02/03.md)
   - [課題](doc/D02/04.md)
3. ユーザ認証をDBと連係する
   - [01](doc/D03/01.md)

### コンポーネントとDBの活用

1. 登録されているユーザの一覧を表示する
   - [01](doc/E01/01.md)
   - [02](doc/E01/02.md)
2. まとめの課題
   - [課題](doc/E02/01.md)

### 選択肢を選ぶ

1. 選択肢を選んで送信する（ドロップダウン）
   - [01](doc/F01/01.md)
   - [02](doc/F01/02.md)
   - [03](doc/F01/03.md)
   - [04](doc/F01/04.md)

### おまけ

1. [独自のバリデーション](doc/Ex01/01.md)
2. Modelを使い分ける
   1. [LambdaModel, CompoundPropertyModelを使った例](wsbp/src/main/java/com/example/wsbp/page/ex/CPMSPage.java)（ソースコードのみ）
      - [参考：Wicket models and forms](https://ci.apache.org/projects/wicket/guide/9.x/single.html#_wicket_models_and_forms)
      - [さらに綺麗にかくとこんな感じ](wsbp/src/main/java/com/example/wsbp/page/ex/HonkiPage.java)（ソースコードのみ）
   2. DBや外部（WEB-APIなど）からデータを取得する時にはLoadableDetachableModelを意識する
      - コード例：[SignedPage.java#L46-L49](wsbp/src/main/java/com/example/wsbp/page/signed/SignedPage.java#L46-L49)
      - [参考：Detachable models](https://ci.apache.org/projects/wicket/guide/9.x/single.html#_detachable_models)
3. コンポーネントを今風に使う
   - [折角なので Apache Wicket 8 についてまとめておきたい（2016-12-23時点）](https://qiita.com/gishi_yama/items/59fae7f2a56df31c5749)
   - [Apache Wicket 8 の代表的な変更点は結局どうなったか（2018-06-26時点）](https://qiita.com/gishi_yama/items/d392088b4c57950fcbb4)
4. [うまくWicketを使うために](doc/Ex04/01.md)
