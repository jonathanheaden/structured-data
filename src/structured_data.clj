(ns structured-data)

(defn do-a-thing [x]
  (let [doublex (+ x x)]
    (Math/pow doublex doublex)
    ))

(defn spiff [v]
  (let [x (get v 0)
        y (get v 2)]
    (cond
      (and (nil? x) (nil? y)) 0
      (nil? y) x
      :else (+ x y)
      )))     

(defn cutify [v]
  (conj v "<3"))

(defn spiff-destructuring [v]
  (let [[x drop y] v]
    (cond
      (and (nil? x) (nil? y)) 0
      (nil? y) x
      :else (+ x y)
      )))  

(defn point [x y]
  [x y])

(defn rectangle [bottom-left top-right]
  [bottom-left top-right])

(defn width [rectangle]
  (let [[[x1 y1] [x2 y2]] rectangle]
    (- x2 x1)
    ))

(defn height [rectangle]
  (let [[[x1 y1] [x2 y2]] rectangle]
    (- y2 y1)
    ))

(defn square? [rectangle]
  (= (width rectangle) (height rectangle)))

(defn area [rectangle]
  (* (width rectangle) (height rectangle)))

(defn contains-point? [rectangle point]
  (let [[[x1 y1] [x2 y2]] rectangle
        [x y] point]
    (and (<= x1 x x2) (<= y1 y y2))
    ))

(defn contains-rectangle? [outer inner]
  (let [[pt1 pt2] inner]
    (and (contains-point? outer pt1) (contains-point? outer pt2))
    ))

(defn title-length [book]
  (count (:title book)))

(defn author-count [book]
  (count (:authors book)))

(defn multiple-authors? [book]
  (> (author-count book) 1))

(defn add-author [book new-author]
  (let [newauthors (conj (:authors book) new-author)] 
    (assoc book :authors newauthors)
    ))

(defn alive? [author]
  (not (contains? author :death-year)))

(defn element-lengths [collection]
  (map count collection))

(defn second-elements [collection]
  (let [getsecond (fn [c] (get c 1))]
    (map getsecond collection)
    ))

(defn titles [books]
  (map :title books))

(defn monotonic? [a-seq]
  (or (apply <= a-seq) (apply >= a-seq)))

(defn stars [n]
  (apply str (repeat n "*")))

(defn toggle [a-set elem]
  (if (contains? a-set elem)
    (disj a-set elem)
    (conj a-set elem)))

(defn contains-duplicates? [a-seq]
  (not (= (count (set a-seq)) (count a-seq))))

(defn old-book->new-book [book]
  (assoc book :authors (set (:authors book))))

(defn has-author? [book author]
  (contains? (:authors book) author))

(defn authors [books]
  (apply clojure.set/union (map :authors books)))

(defn all-author-names [books]
  (set (map :name (authors books))))

(defn author->string [author]
  (let [name (:name author)
        years
        (cond
          (contains? author :death-year) (apply str  (interpose " - " [(:birth-year author) (:death-year author)] ))
          (contains? author :birth-year) (str (:birth-year author) " - ")
          :else nil
          )]
    (if (nil? years)
      (str name)
      (apply str name " (" years ")"))     
    ))

(defn authors->string [authors]
  (apply str (interpose ", " (map author->string authors))))

(defn book->string [book]
  (apply
   str (interpose ", written by " [(:title book)
                                   (authors->string (:authors book))]
                  )))

(defn books->string [books]
  (cond (= (count books) 0) "No books."
        (= (count books) 1) (apply str "1 book. " (book->string (first books)) ".")
        :else  (str (apply str (count books) " books. " (apply str (interpose ". " (map book->string books)))) ".")
        ))

(defn books-by-author [author books]
  (filter (fn [book] (has-author? book author)) books))

(defn author-by-name [name authors]
  (first (filter (fn [auth] (= name (:name auth))) authors)))

(defn living-authors [authors]
  (filter alive? authors))

(defn has-a-living-author? [book]
  (not (empty? (living-authors (:authors book)))))

(defn books-by-living-authors [books]
  (filter (fn [book] (has-a-living-author? book)) books))

; %________%
