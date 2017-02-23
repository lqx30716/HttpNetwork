# HttpNetwork
基于httpURLconnection的一个简单的网络请求框架，包含GET和POST请求方法，底层采用原生 ThreadPoolExecutor + Runnble + Handler实现，
支持string类型数据，表单形式的数据，以及文件类型数据，多文件数据以及分块上传等操作，支持文件下载，可查看下载进度等简单功能
