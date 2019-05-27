# availc

A simple CLI for empty classroom queries, built for BUAA students. Part of the Object Oriented curriculum 2019 of Software School.

## 开发依赖

Parser 类依赖于 Chrome Driver 和 Chrome 浏览器。如果要运行 Parser 类去实际地解析网页，先下载对应于自己 Chrome 浏览器版本的 Chrome Driver，然后去修改 Parser 类构造方法 `Parser(boolean)` 的 DEAFULT_CHROME_DRIVER_PATH 变量为 Chrome Driver 在本地存储的路径。
