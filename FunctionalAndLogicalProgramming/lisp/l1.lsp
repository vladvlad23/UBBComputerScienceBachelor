(defun nthElement (l poz)
    (cond
    ((equal 0 poz) (write(car l)))
    (t (nthElement (cdr l) (- poz 1)))
    )
)

(defun exist (L e)
    (cond
        ((null L) nil)
        ((equal e (car L)) t)
        ((not(listp (car L))) (exist(cdr L) e))
        ((exist (car L) e) t)
        ((exist (cdr L) e) t)
    )
)


(defun numberOfSublists (L)
    (cond 
        ((null L) 1)
        ((listp (car L)) (+ (numberOfSublists (cdr L)) (numberOfSublists (car L))))
        ((numberOfSublists (cdr L)))
    )
)

(defun turnListToSet (L)
    (cond
        ((null L) nil)
        ((not(exist (cdr L) (car L))) (cons (car L) (turnListToSet (cdr L))))
        ((turnListToSet (cdr L)))
    ) 
)