 %Functions for this laboratory
disp("Start laboratory")
function val = computeDividedDifferenceTable(x,y)
  lineNumber = size(x,2);
  columnNumber = lineNumber + 1 ;
  matrix = zeros(lineNumber,columnNumber);
  matrix(:,1) = x;
  matrix(:,2) = y;
  for k=3:columnNumber
    for i=1:(--lineNumber)
      matrix(i,k) = ( matrix(i+1,k-1) - matrix(i,k-1) ) / ( x(i+(k-2)) - x(i) );
    endfor
  endfor
  val = matrix;
endfunction

function val = computeNewtonSum(x,xList,m,dividedDifferenceTableFirstLine)
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

function val = Nmf (x, xList, yList, table)
  val = yList(1) + computeNewtonSum(x,xList, size(xList,2) -1 , table);
endfunction

%1
disp("First problem. A")
xList=[1,1.5,2,3,4];
yList=[0,0.17609,0.30103,0.47714,0.60206];
toApproximateFirst = 2.5;
toApproximateSecond = 3.25;
table = computeDividedDifferenceTable(xList,yList)(1,:);
disp("First approx: ")
Nmf(toApproximateFirst,xList,yList,table)
disp("Second approx: ")
Nmf(toApproximateSecond,xList,yList,table)

i=10:35;
yi=i./10;

resultYi = Nmf(yi,xList,yList,table);
disp("And the error with the given parameters is: ")
error = max(abs(log10(yi) - resultYi))

%2
%a 
disp("Second problem. A")

xList = [1,2,3,4,5];
yList = [22,23,25,30,28];

xToApproximate = 2.5;
table = computeDividedDifferenceTable(xList,yList)(1,:);
Nmf(xToApproximate,xList,yList,table) 

%b

figure(1)
plot(xList,yList,"*");
hold on
rangeForNewton = 0:0.01:6;
resultForEstimation = [];
for i = 1:numel(rangeForNewton)
  resultForEstimation = [resultForEstimation ; Nmf(rangeForNewton(i),xList,yList,table)];
endfor
plot(rangeForNewton,resultForEstimation,"");

%Observation: since the line passes through all the points and then
%goes mental (because of limited data available), i assume it works properly

%3

function val = consideredFunction(x)
  val = e.^(sin(x));
endfunction

interval = 0:6;
intervalResults = consideredFunction(interval);
table = computeDividedDifferenceTable(interval,intervalResults)(1,:);

consideredPoints = 0:7/13:6;
consideredValues = Nmf(consideredPoints,interval,intervalResults,table);

figure(2)
plot(interval,intervalResults,"*")
hold on
plot(consideredPoints,consideredValues,"")

%Observation: I'm not sure if i understood properly
%exactly if this is how I should have created the N12f in this particular instance
%but it's the best i could do and the graph shows ok

%4
disp("Onto the Aitken's algorithm")
x = 1:2:115;
y = sqrt(x);
m = length(x);
f = [y'];
X = 115
for i = 1:m
  for j = 1:i-1
    determinant = det([f(j, j), x(j) - X; f(i, j), x(i) - X]);
    f(i, j+1) = 1 / (x(i) - x(j)) * determinant;
  end
end
disp("approximation of x: ")
approxOfX = f(m, m)
%It's close enough that sqrt(115) - approxOfX yield 0 with default formatting
