disp("Start laboratory")

function result = RepeatedRectangleRemainder(d2f, n, a, b)
  xi = (a + b) ./ 2;
  result = ((b - a) .^ 3) ./ (24 .* (n .^ 2)) .* d2f(xi);
endfunction

function result = RepeatedRectangle(f, d2f, n, a, b)
  sum = 0;
  x = 0;
  for i = 1:n
    if (i == 1)
      x = a + (b - a) ./ (2 .* n);
    else
      x = a + (b - a) ./ (2 .* n) + (i - 1) .* (b - a) ./ n;
    endif
    sum = sum + f(x);
  endfor
  sum = (b - a) ./ n .* sum;
  result = sum + RepeatedRectangleRemainder(d2f, n, a, b);
endfunction

function result = RombergTrapezium(f, a, b, eps)
  h = b - a;
  qtactual = h ./ 2 .* (f(a) + f(b));
  qtprev = qtactual + 100000;
  k = 1;
  while (abs(qtactual - qtprev) > eps)
    qtprev = qtactual;
    x = a + (2 .* (1:(2.^(k - 1))) - 1) ./ (2 .^ k) .* h;
    qtactual = qtprev ./ 2 + h ./ (2 .^ k) .* sum(f(x));
    k = k + 1;
  endwhile
  result = qtactual;
endfunction

function result = RemainderTrapezium(d2f, a, b, n)
  xi = (a + b) / 2;
  result = - (b - a) .^ 3 / (12 * n .^ 2) * d2f(xi);
endfunction

"Repeated trapezium formula 1"

function result = RepeatedTrapezium(f, d2f, a, b, n)
  k = 1:n-1;
  h = (b - a) / n;
  x = a + k .* h;
  y = f(x);
  result = (b - a) / (2 * n) .* (f(a) + f(b) + 2 * sum(y)) + RemainderTrapezium(d2f, a, b, n);
endfunction

"Remainder Simpson 1"

function result = RemainderSimpson(d4f, a, b, n)
  xi = (a + b) / 2;
  result = - (b - a) .^ 5 / (2880 * n .^ 4) * d4f(xi);
endfunction

"Repeated Simpson formula 1"

function result = RepeatedSimpson(f, d4f, a, b, n)
  h = (b - a) / n;
  k1 = 1:n;
  k2 = k1 - 1;
  y1 = f(((a + k1 .* h) + (a + k2 .* h)) ./ 2);
  k3 = 1:n-1;
  y2 = f(a + k3 .* h);
  result = (b - a) ./ (6 * n) .* (f(a) + f(b) + 4 .* sum(y1) + 2 .* sum(y2)) + RemainderSimpson(d4f, a, b, n);
endfunction

function result = RombergAitken(f, d2f, a, b, eps)
  done = 0;
  prev = [RepeatedTrapezium(f, d2f, a, b, 1)];
  i = 2;
  while (done == 0)
    actual = zeros(1, i);
    actual(1) = RepeatedTrapezium(f, d2f, a, b, i);
    for k=2:i
      actual(k) = (4 .^ (-k+1) .* prev(k - 1) - actual(k - 1)) ./ (4 .^ (-k+1) - 1);
    endfor
    last_prev = prev(i - 1);
    last_actual = actual(i);
    if (abs(last_actual - last_prev) <= eps)
      done = 1;
    endif
    prev = actual;
    i = i + 1;
  endwhile
  result = actual(i - 1);
endfunction

function result = AdaptiveSimpson(f, d4f, a, b, eps)
  mid = (a + b) ./ 2;
  i1 = RepeatedSimpson(f, d4f, a, b, 1);
  y1 = RepeatedSimpson(f, d4f, a, mid, 1);
  y2 = RepeatedSimpson(f, d4f, mid, b, 1);
  i2 = y1 + y2;
  if (abs(i1 - i2) < 15 .* eps)
    result = i2;
    return;
  else
    result = AdaptiveSimpson(f, d4f, a, mid, eps ./ 2) + AdaptiveSimpson(f, d4f, mid, b, eps ./ 2);
  endif
endfunction



# First problem
a = 1;
b = 1.5;
n1 = 1;
n2 = 150;
n3 = 500;
givenFunction = @(x)(exp(-(x .^ 2)));
derivativeOfGIvenFunction = @(x)(exp(-(x .^ 2)) - (4 .* (x .^ 2) - 2));
rectangle1 = RepeatedRectangle(givenFunction, derivativeOfGIvenFunction, n1, a, b)
rectangle150 = RepeatedRectangle(givenFunction, derivativeOfGIvenFunction, n2, a, b)
rectangle500 = RepeatedRectangle(givenFunction, derivativeOfGIvenFunction, n3, a, b)
x = a:0.01:b;
y = givenFunction(x);
figure(1);
plot(x, y);
hold on;
plot([a a b b], [0 givenFunction((a + b) / 2) givenFunction((a + b) / 2) 0]);


# Second Problem

a = 0;
b = 1;
givenFunction = @(x)(2 ./ (1 + x .^ 2));
derivativeOfGIvenFunction = @(x)(2 .* ((8 .* (x.^2))./((1 + x.^2).^3) - 2./((1 + x.^2).^2)));
eps = 10 .^ (-4);
rombergTrapezium = RombergTrapezium(givenFunction, a, b, eps)
rombergAitken = RombergAitken(givenFunction, derivativeOfGIvenFunction, a, b, eps)

# Third Problem
a = 1;
b = 3;
figure(2);
givenFunction = @(x)(100 ./ (x .^ 2) .* sin(10 ./ x));
derivativeOfGIvenFunction = @(x)((4000 .* (20 .*  x .* (3 .* (x.^2) - 25) .* cos(10./x) + (3 .* (x.^4) - 300 .* (x.^2) + 250) .* sin(10./x)))./(x.^10));
x = a:0.01:b;
y = givenFunction(x);
eps = 10 .^ (-4);
plot(x, y);
adaptiveSimpson = AdaptiveSimpson(givenFunction, derivativeOfGIvenFunction, a, b, eps)
simpson50 = RepeatedSimpson(givenFunction, derivativeOfGIvenFunction, a, b, 50)
simpson100 = RepeatedSimpson(givenFunction, derivativeOfGIvenFunction, a, b, 100)
