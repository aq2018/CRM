
apache commons-fileupload
	非常有名的组件，专门做文件上传的，大多数框架集成的文件上传组件都是它。
	
	commons-fileupload组件依赖：commons-io
	
	他们两个子组件都属于apache commons组件中的成员。

文件上传的时候保存到服务器硬盘中：
	在WebContent目录下（在web项目的根路径下，新建了一个files目录），用来存储文件。

使用以上两个组件，需要引入相关jar包，使用这些jar当中的API来完成文件上传。
	commons-fileupload-1.3.2.jar
	commons-io-2.5.jar

在WebContent目录下新建一个tmp目录，用来存储临时文件。

重点内容：
	在web项目中，怎么获取目录的绝对路径：
		String tmpPath = application.getRealPath("tmp");
		tmp在项目的根路径下。
	
	类路径下的文件的绝对路径怎么获取？
		String absolutePath = Thread.curentThread().getContextClassLoader().getResource("直接从类的根路径下作为起点开始找").getPath();
	