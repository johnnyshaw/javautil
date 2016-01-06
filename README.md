# javautil
# problem
# 1. eclipse pom 文件内容出现问题，引入commons-lang3包时乱码，出现空格问题，原因windows空格无法识别，导致maven无法编译，慎用空格。
<dependency>
		????<groupId>org.apache.commons</groupId>
		????<artifactId>commons-lang3</artifactId>
		????<version>3.1</version>
		</dependency>
		
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-lang3</artifactId>
			<version>3.1</version>
		</dependency> 