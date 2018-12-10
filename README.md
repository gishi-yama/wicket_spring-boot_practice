# Wicket Spring-Boot Practice

本プロジェクトは、千歳科学技術大学のプロジェクトメンバー用の講習資料である。

研究で独自開発するシステムのベースに使うJava Webフレームワークの組み合わせの中で、ユーザ作成機能、ログイン機能の実装を体験する。

これにより、クラシックなサーバサイドWebアプリケーションの作り方を学ぶ。

## 動作確認環境

- Oracle OpenJDK 11
- Wicket-Spring-Boot 2.1.5
    - Wicket 8
    - Spring Boot 2
    - Spring 5
    - H2DB（PostgreSQL互換モード）
    - 他
- macOS 10.13
- IntelliJ IDEA 2018.2

※ 学内開発ではPostgreSQLをデータベースに使うことが多いが、環境設定の簡単さを重視してH2DBのPostgreSQLモードを使う。

## 目次

### フレームワークの説明と開発の準備

1. [Apache Wicket](doc/A01/01.md)
1. [SpringとSpring Boot](doc/A02/01.md)
1. 開発の準備

### 動作確認

1. [WebPageを表示する](doc/B01/01.md)
1. [WebPageに表示するものをSpringで作る](doc/B02/01.md)
1. [WebPage間を移動する](doc/B03/01.md)

### ユーザ追加

1. ユーザ追加のフォームを作る
1. データベースに登録情報を登録する
1. データのバリデーションを設定する

### サインイン

1. サインインフォームを作成する
1. サインインの基本ロジックを作成する
1. 登録されているユーザの一覧を表示する

### おまけ

1. Modelを使い分ける
1. ステートレス/ステートフル
1. コンポーネントを今風に使う