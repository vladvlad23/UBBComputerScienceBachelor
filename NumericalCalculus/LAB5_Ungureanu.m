disp("Start laboratory")
function val = computeDividedDifferenceTable(x,y,derivatives)
  lineNumber = size(x)(2);
  columnNumber = lineNumber + 1 ;
  matrix = zeros(lineNumber,columnNumber);

  matrix(:,1) = x;
  matrix(:,2) = y;
  derivatives = kron(derivatives,[1 1]);
  for i=1:(--lineNumber)
    if (rem(i,2)==0)
      k = 3;
      matrix(i,k) = ( matrix(i+1,k-1) - matrix(i,k-1) ) / ( x(i+(k-2)) - x(i) );
    else
      matrix(i,3) = derivatives(i);
    endif
  endfor
  for k=4:columnNumber
    for i=1:(--lineNumber)
      matrix(i,k) = ( matrix(i+1,k-1) - matrix(i,k-1) ) / ( x(i+(k-2)) - x(i) );
    endfor
  endfor
  val = matrix;
endfunction

function val = computeHermiteSum(x,xList,m,dividedDifferenceTableFirstLine)
  val = 0;
  for i=1:m
    p = 1;
    for j=1:i
      p = p .* (x - xList(j));
    endfor
    p = p .* dividedDifferenceTableFirstLine(i+2);
    val = val + p; 
  endfor
endfunction

function val = computeHermitePol (x, z, f, derivatives)
    z = kron(z,[1 1]);
    f = kron(f,[1 1]);
    table = computeDividedDifferenceTable(z,f,derivatives)(1,:);
    val = f(1) + computeHermiteSum(x,z, (size(z)(2))-1, table);
endfunction


% First problem %
z = [0,3,5,8,13];
f = [0,225,383,623,993];
derivatives = [75,77,80, 74, 72];

disp("Hermite polynomial distance result for time t=10: ")
computeHermitePol(10,z,f,derivatives)


% Second problem %
disp("Start second problem: ")
z = [1,2];
f = [0,0.6931];
derivatives = [1,0.5];

disp("The value of the Hermite polynomial at 1.5: ")
H = computeHermitePol(1.5,z,f,derivatives)

disp("The absolute appproximation error is: ")
error = abs(log(1.5) - H)

% Third problem %

function val = sin2x(x)
  val = sin(2*x);
endfunction

function val = derivativeSin2x(x)
  val = 2*cos(2*x);
 endfunction
 

disp("Start third problem (look at plot): " )
actualData = -5:6;
x = linspace(-5,6,15);
derivatives = derivativeSin2x(actualData);

H = computeHermitePol(x,actualData,sin2x(actualData),derivatives);
figure(1)
axis([-6 6 -1 1])
plot(actualData,sin2x(actualData),"*")
hold on
plot(x,H,"")
legend("Actual function","Hermite Polynomial")
title("Third problem")
xlabel("Value of x")
ylabel("Value of sin(2x) and prediction")



disp("Starting facultative problems: ")
%%%%%%%%% BONUS FACULTATIVE %%%%%%%%%%%%
% Fourth problem %

disp("Starting facultative problem 4: ")

xData = [8.3,8.6];
fXData = [17.56492, 18.50515];
fDerivatedXData = [3.116256, 3.151762];

disp(" The result for f(8.4) ")
result = computeHermitePol(8.4,xData,fXData, fDerivatedXData)


% Fifth problem %
disp("Starting facultative problem 5: ")
function val = givenFunction(x)
  val = 3.*x.*(e.^x) - e.^(2.*x);
endfunction

function val = derivativeOfGivenFunction(x)
  val = e.^x .* ( 3 .* x - 2 * e.^x + 3);
endfunction


xData = [1, 1.05];
fXData = givenFunction(xData);
fDerivatedXData = derivativeOfGivenFunction(xData);

disp("The result of f(1.03) with degree at most 3: ")
result = computeHermitePol(1.03,xData,fXData,fDerivatedXData)

disp("The result of f(1.03) with degree at most 5: ")

xData = [xData, 1.07]
fXData = [fXData, givenFunction(1.07)]
fDerivatedXData = [fDerivatedXData, derivativeOfGivenFunction(1.07)];

%Note: these are either equal, or the formatting of my octave is
%not set to have enough precision. Hopefully it is ok
result = computeHermitePol(1.03, xData,fXData, fDerivatedXData)
