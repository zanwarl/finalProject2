<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>

<style>

*{text-align:center;}
</style>
</head>
<body>
<form name="comReq" action="comReqWrite.do" method="post">
<table>
	<tr>
		<th>�Ű���</th>
		<td><input type="text" name="sender_idx"></td>
	</tr>
	<tr>
		<th>�Ű�����</th>
		<td><input type="text" name="recever_idx"></td>
	</tr>
	<tr>
		<th>�з�</th>
		<td><select name="comreq_cate">
			<option value="1">ȣ��Ʈ->�Խ�Ʈ</option>
			<option value="2">�Խ�Ʈ->ȣ��Ʈ</option>
			<option value="3">�Ϲ�</option>
		</select></td>
	</tr>
	<tr>
		<th>����</th>
		<td><textarea rows="10" cols="30" name="comreq_con"></textarea></td>
	</tr>
	<tr>
		<td colspan="2">
		<input type="submit" value="����">
		<input type="reset" value="�ٽ��ۼ�">
		</td>
	</tr>
</table>
</form>
</body>
</html>