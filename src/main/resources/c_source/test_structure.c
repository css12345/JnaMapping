#include<stdio.h>
#define Stack_Size 20
#define FALSE 0
#define TRUE 1
typedef struct{
	char elem[Stack_Size];
	int top; 
}SeqStack;
void InitStack(SeqStack *S){
	S->top = -1;
}
int Push(SeqStack *S,char x){
	if(S->top == Stack_Size - 1)
		return FALSE;
	S->top++;
	S->elem[S->top] = x;
	return TRUE;
}
int Pop(SeqStack *S,char *x,int j){
	if(S->top == -1)
		return FALSE;
	else{
		int i;
		i = S->top;
		x[j - i] = S->elem[S->top];
		S->top--;
		x[j - i + 1] = '\0';
		return TRUE;
	}	
}
__declspec(dllexport) void RadixConversion(int n,int r,SeqStack *S,char *x){
	int remainder,quotient;
	do{
		quotient = n / r;
		remainder = n % r;
		char c;
		int j;
		if(remainder >= 0&&remainder <= 9)
			c = (char)(48 + remainder);
		else c = (char)(65 + remainder - 10);	
		j =	Push(S,c);
		n = quotient; 
	}while(quotient != 0);
	int i = S->top;
	while(Pop(S,x,i))
		Pop(S,x,i);
}
int main(){
	SeqStack s;
	int n,r,i;
	char ch[Stack_Size],c = 'A';
	printf("输入一个正整数及要转换成的进制数：");
	scanf("%d%d",&n,&r);
	if(n <= 0)
		printf("输入的数不是正整数");
	else if(r > 72||r <= 1)
		printf("不能转换成该进制");
	else{
		for(i = 10;i < r;i++,c++)
			printf("%d在%d进制中为%c\n",i,r,c);
		InitStack(&s);
		RadixConversion(n,r,&s,ch);
		printf("\n%d转换成%d进制为：%s",n,r,ch);
	}	 
}
