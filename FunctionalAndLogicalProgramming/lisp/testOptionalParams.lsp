(defun myand (a &rest l)
    nil
)

(defun myOther(&rest a)
    nil
)

(defun check (list)
    (cond
    ((listp list) (equal (car list) 5) t)
    (t nil) 
    )
)



(defun flatten (l)
  (cond ((null l) nil)
        ((atom l) (list l))
        (t (loop for a in l appending (flatten a)))))



(defun contains (givenList givenAtom)
    (cond
        ((atom givenList) (eq givenList givenAtom))
        ((or (flatten (mapcar #'(lambda (b) (contains b givenAtom)) givenList))) t)
    )
)