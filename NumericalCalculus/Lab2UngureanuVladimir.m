disp("First problem")

x=0:0.1:1;
l1=x;
l2=3/2.*x.^2-1/2;
l3=5/2.*x.^3.-3/2.*x;
l4=35/8.*x.^4.-15/4.*x.^2.+3/8;
figure(1)
title("First problem . Plotting Legendre")
subplot(2,2,1)
plot(x,l1)
subplot(2,2,2)
plot(x,l2)
subplot(2,2,3)
plot(x,l3)
subplot(2,2,4)
plot(x,l4)


%2. Chebyshev Polynomial


%a)

disp("Second problem. A)")
figure(2)
title("Second problem. Plotting simple Chebyshev")
t=-1:0.01:1;
function firstChebyshevPlot(n,t)
  val = cos(n .* acos(t));
  plot(t,val)
endfunction
hold on
for n=1:3
    firstChebyshevPlot(n,t)
endfor

disp("Second problem. B)")
t=-1:0.01:1;
figure(3)
title("Second problem. Plotting higher order Chebyshev")
function val = higherChebyshev(n,t)
    x=-1:0.01:1;
    if n==0
        val = 1;
    elseif n==1
        val = x;
    else
        val = 2.*x.*higherChebyshev(n-1)-higherChebyshev(n-2);
    end
endfunction
hold on
t=-1:0.01:1;
for n=1:5
    plot(t,higherChebyshev(n))
endfor
hold off

%P3
disp("Third problem.")
figure(4)
title(" Taylor plotting ") 
f=exp(0);
n=7;
k=0:n;
function val = pol(x,n)
val=0;
  for k=0:n 
      val = val+ x^k/factorial(k); 
  end
endfunction
hold on
for n=1:6
    for x=-1:0.01:3
        plot(x,pol(x,n))
    endfor
endfor
    hold off
    
%P4

disp("Fourth problem.")
h = 0.25;
function val = givenFunction(x)
  val = sqrt(5*(x^2)+1);
endfunction
lineNumber=7;
columnNumber=8;
matrix = zeros(lineNumber,columnNumber);
matrix(:,1) = 0:6;
for i=1:lineNumber
  matrix(i,2) = givenFunction(matrix(i,1));
endfor
for i=3:columnNumber
  for j=1:(--lineNumber)
    matrix(j,i) = matrix(j+1,i-1) - matrix(j,i-1);
  endfor
endfor
matrix

%P5

lineNumber=4;
columnNumber=5;
matrix = zeros(lineNumber,columnNumber);
x = [2,4,6,8]
matrix(:,1) = x
matrix(:,2) = [4,8,14,16]
for k=3:columnNumber
  for i=1:(--lineNumber)
    matrix(i,k) = ( matrix(i+1,k-1) - matrix(i,k-1) ) / ( x(i+(k-2)) - x(i) );
  endfor
endfor
matrix
