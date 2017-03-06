package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionTest {

	public static void main(String[] args) {

		if(args.length < 2){
			System.err.println("ERROR");
			System.exit(0);
		}
		
		System.out.println("Input:\"" + args[0] + "\"");
		
		for(String arg : args){
			System.out.println("Regular expression: \"" + arg +"\"");
			Pattern p = Pattern.compile(arg);
//			Pattern.matches(regex, input)
			Matcher m = p.matcher(args[0]);
			while(m.find()){
				System.out.println("Match \"" + m.group() + "\" at positions " +  
						m.start() + "-" + (m.end() - 1));
			}
		}
	}

}

//hi  hi,HI,Hi,hI him,history,high
//\bhi\b
//假如你要找的是hi后面不远处跟着一个Lucy，你应该用\bhi\b.*\bLucy\b
//.是另一个元字符，匹配除了换行符以外的任意字符
//.*连在一起就意味着任意数量的不包含换行的字符
//\s匹配任意的空白符，包括空格，制表符(Tab)，换行符，中文全角空格等
//\w匹配字母或数字或下划线或汉字等
//[a-z0-9A-Z_]也完全等同于\w

/*
 * 下面是一个更复杂的表达式：\(?0\d{2}[) -]?\d{8}。

“(”和“)”也是元字符，后面的分组节里会提到，所以在这里需要使用转义。

这个表达式可以匹配几种格式的电话号码，
像(010)88886666，或022-22334455，或02912345678等。我们对它进行一些分析吧：
首先是一个转义字符\(,它能出现0次或1次(?),然后是一个0，后面跟着2个数字(\d{2})，
然后是)或-或空格中的一个，它出现1次或不出现(?)，最后是8个数字(\d{8})。
 * */


/*正则表达式里的分枝条件指的是有几种规则，如果满足其中任意一种规则都应该当成匹配，具体方法是用|把不同的规则分隔开。听不明白？没关系，看例子：

0\d{2}-\d{8}|0\d{3}-\d{7}这个表达式能匹配两种以连字号分隔的电话号码：一种是三位区号，8位本地号(如010-12345678)，一种是4位区号，7位本地号(0376-2233445)。*/

//	\d{1,3}匹配1到3位的数字