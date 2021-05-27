poly1 = [2 -5 0 8]
x1 = 2
poly2 = [1 -5 -17 21]

polyval(poly1,x1)
roots(poly2)

% Third part: Graphs

%First exercise
figure(1)
x = 0: 0.01 : 1;
func = exp(10 * x .* (x-1)) .* sin(12*pi*x)
plot(x,func)
hold on
func2 = 3*exp(5*x.^2-1).*cos(12*pi*x)
plot(x,func2)
hold off

%Second exercise
figure(2)
a=5;
b=4;
t=0:0.01:10*pi;
x = (a+b) * cos(t) - b * cos((a / b + 1) * t)
y = (a+b) * sin(t) - b * sin((a / b + 1) * t)
plot(x,y)

%Third exercise
figure(3)
x=0:0.01:2*pi;
f1 = cos(x);
f2 = sin(x);
f3 = cos(2*x);
plot(x,f1, 'r');
hold on;
plot(x,f2, 'g');
plot(x,f3);

%Fourth exercise
figure(4)
x1 = -1: 0.01: 0
x2 = 0.01: 0.01: 1
y1 = x1 .^ 3 + sqrt(1 - x1)
y2 = x2 .^ 3 - sqrt(1 - x2)
plot(x1,y1)
hold on
plot(x2,y2)

%Fifth exercise
figure(5)
x = 0 : 50
x_even = (1 - mod(x, 2)) .* x
x_odd = mod(x, 2) .* x
y_even = x_even / 2
y_odd = 3 * x_odd + 1
plot(x_even, y_even, 'x')
hold on
plot(x_odd, y_odd, 'x')

##Sixth exercise 
g=2
for i=1:5
  g=1+1/g
end
g


 %Seventh exercise
figure(6)
[X,Y] = meshgrid(-2:0.1:2, -4:0.1:4);
Z = exp((-(X-1/2).^2-(Y-1/2).^2));
mesh(X,Y,Z);