disp("Starting laboratory: ")

function val = partialDerivativeOfA(m,xValues,functionValues)
  numerator = (m+1) * sum(xValues.*functionValues) - sum(xValues) * sum(functionValues);
  denominator = (m+1) * sum(xValues.^2) - (sum(xValues)^2);
  
  val = numerator/denominator;
endfunction

function val = partialDerivativeOfB(m,xValues,functionValues)
  numerator = sum(xValues.^2) * sum(functionValues) - sum(xValues.*functionValues) * sum(xValues);
  denominator = (m+1) * sum(xValues.^2) - (sum(xValues)^2);
  val = numerator / denominator;
endfunction

function val = ELinear(a,b,xValues,functionValues)
  phiValues = phiLinear(a,b,xValues);
  val = sum((functionValues - phiValues).^2);
endfunction

function val = phiLinear(a,b,x)
  
  val = a * x + b;
endfunction

% First problem %
timeValues = [1,2,3,4,5,6,7];
temperatureValues = [13,15,20,14,15,13,10];

a = partialDerivativeOfA(size(timeValues)(2)-1, timeValues, temperatureValues);
b = partialDerivativeOfB(size(timeValues)(2) -1, timeValues, temperatureValues);

disp("The resulting temperature at 8 is: ")
phiLinear(a, b, 8)

disp("The minimum value E is: ")
ELinear(a,b,timeValues,temperatureValues)

interval = linspace(1,7);
leastSquareValues = phiLinear(a,b,interval);

figure(1)
title("First problem")
plot(timeValues,temperatureValues,"o")
hold on
plot(interval,leastSquareValues,"")
legend("given data","phi")

% Second problem %

disp("The second problem: ")
temperatureValues = [0,10,20,30,40,60,80,100];
pressureValues = [0.0061,0.0123,0.0234,0.0424,0.0738,0.1992,0.4736,1.01333];
firstPolynomial = polyfit(temperatureValues,pressureValues,4);
secondPolynomial = polyfit(temperatureValues,pressureValues,9);

disp("The values of T=45 for the polynomials are: ")
firstApprox = polyval(firstPolynomial,45)
secondApprox = polyval(secondPolynomial,45)

disp("Approximation error for the polynomials are: ")
errorFirstApprox = firstApprox - 0.095848
errorSecondApprox = secondApprox - 0.095848

%Obs: I would have expected a higher degree polynomial to have a smaller error.
%Hopefully, The result is correct and I was not wrong


% Third problem %

disp("Starting third problem: ")
%i have no idea how to take points in that specific plane [0.3]x[0.5]

figure(2)
title("Third Problem")
axis([0 3 0 5]);
[pointsX,pointsY] = ginput(10);

coefficients = polyfit(pointsX,pointsY, 2);
interval = linspace(min(pointsX),max(pointsX));
approximation = polyval(coefficients, interval);

plot(pointsX,pointsY,"o")
hold on
plot(interval,approximation, "")
legend("points", "approximation")







