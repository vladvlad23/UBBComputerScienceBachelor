xArray = [1930, 1940, 1950, 1960, 1970, 1980];
functionArray = [123203, 131669, 150697, 179323, 203212, 226505];

function val = uix(x,vect)
  val = 1;
  for j=1:size(vect,2)
      if (vect(j)!=x) 
        val = val*(x-vect(j));
      endif
   endfor
endfunction

function val = Ai(x,vect)
  val = 1/uix(x,vect);
endfunction

function val = linearInterpolation(x, vectx, vectfx)
  sumUp = 0;
  sumDown = 0;
  for i=1:size(vectx,2)
    %this if is the only way i got it to not divide by 0
    %not sure if it's correct, but that's all I could do
      if (x-vectx(i) == 0)
          sumUp = sumUp + Ai(vectx(i),vectx)*vectfx(i)/(x);
          sumDown = sumDown + Ai(vectx(i),vectx)/(x);
      else 
          sumUp = sumUp + Ai(vectx(i),vectx)*vectfx(i)/(x-vectx(i));
          sumDown = sumDown + Ai(vectx(i),vectx)/(x-vectx(i));
      endif
  endfor
  val = sumUp/sumDown;
endfunction

disp("First problem")
linearInterpolation(1975,xArray,functionArray)
linearInterpolation(1995,xArray,functionArray)

%Second problem
disp("Second problem")
secondProblemArray = [100,121,144];
secondProblemFunctionArray = [10,11,12];
linearInterpolation(115,secondProblemArray,secondProblemFunctionArray)

%started from 0.1 to avoid division by 0 in linearInterpolation
x = [0.1:0.1:10];
thirdFunction = ( 1 + cos(pi .* x) ) ./ ( 1 + x );
figure(1)
title("Plotting the interpolation compared to the function")
plot(x,thirdFunction,'b')
hold on
interval = 0.1:(10/21):10;
interpolatedValues = zeros(21);
for i=1:21
  interpolatedValues(i) = linearInterpolation(interval(i),x,thirdFunction);
endfor
plot(interval,interpolatedValues,'r')


