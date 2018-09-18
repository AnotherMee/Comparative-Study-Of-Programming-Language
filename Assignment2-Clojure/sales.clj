(require ['clojure.string :as 'str])

;for reading data from file
(defn readfile [file]
  	(def data(map #(str/split % #"\|")(str/split-lines (slurp file))))
  	(def formatdata(map #(hash-map(keyword (first %1)) (vec (rest %1))) data)) 
  	(def mergeddata(apply merge formatdata))
  	(into (sorted-map) mergeddata)
)

;Reads data from files
(def customerdata(readfile "cust.txt"))
(def productdata(readfile "prod.txt"))
(def salesdata(readfile "sales.txt"))

;for printing Customer,Product,Sales Data
(defn printdata[data, table]
	(println " ")
	(doseq [[k v] data]
		(if (= table 1)
		(do
			(println  (str (name k)) ":"  "["(get v 0) "," (get v 1) "," (get v 2)"]")
		))
		(if (= table 2)
		(do
			(println  (str (name k)) ":"  "["(get v 0) "," (get v 1)"]")	
		))
		(if (= table 3)
		(do 
			(println (name k)":" "[" (get(get customerdata(keyword (get v 0)))0) "," (get(get productdata(keyword (get v 1)))0) "," (get v 2) "]")
		)
		)
	)
)

;to match product id
(defn prodvalue[value]
	(first (reduce (fn [m [ k v]]
		(if (= (name k) (str value))
			(conj m (get v 1))
			m))
		#{} productdata)))
	
;for printing Total Sales for Customer
(defn totalSales[salesdata]
	(println "Enter Customer Name:")
	(def custinput(read-line))
	(doseq [[k v ] customerdata]
		(if(= (str/lower-case (get v 0)) (str/lower-case(str custinput)))
			(do
			(def custname (get v 0))
			(def customerid(str (name k)))
			
			)
		)
	)
	(doseq [[m n]salesdata]
		(if (=(get n 0) customerid)
			(def salescustid(get n 0))
		)
	)

	(if (= (str/lower-case custname) (str/lower-case (str custinput)))
		(do 
			(if (= salescustid customerid)
				(do
					(println custinput ":" (format "$%.2f"(reduce  + (into [](reduce (fn [m [k v]]
			(if (= (get v 0) customerid)
				(do
				(def productprice(Float/parseFloat (prodvalue (str (get v 1))))) 
				(def productvalue(Float/parseFloat (get v 2)))
				(conj m (*  productprice productvalue)))
				 m)) 
		#{} salesdata)))))			
				)
			)
			(if (not= salescustid customerid)
				(println custinput ":$0")
			)
			)
	)
	(if (not= (str/lower-case custname) (str/lower-case (str custinput)))
		(do 
		(println "Customer Name" custinput "not exist in data")
		)
	)
)

;for Total Count for Product
(defn productCount[]
	(def sum 0)
	(println "Enter Product Name:")
	(def productname(read-line))
	(doseq [[k v] productdata]
		(if (= (str/lower-case(get v 0)) (str/lower-case(str productname)))

			(do (def prodName(get v 0))
				(def prodid(name k)))
		)
	)
	(if (= (str/lower-case prodName) (str/lower-case(str productname)))
		(do
				(doseq [[k v] salesdata]
					(if (= (get v 1) prodid)
						(do 
							(def counter(Integer/parseInt(get v 2)))
							(def sum(+ sum counter))
						)
					)
				)
			(println productname ":" sum)
			)
		)
		
	(if (not= (str/lower-case prodName) (str/lower-case(str productname)))
		(do (println "Product" productname "not exist."))
	)
	
)	
			
(defn goodbye[]
	(println "Good Bye!")
	(System/exit 0))
			
(defn displayMenu []
	(println " ")
	(println "*******  Sales Menu  *******")
	(println "----------------------------")
	(println "1. Display Customer Table")
	(println "2. Display Product Table")
	(println "3. Display Sales Table")
	(println "4. Total Sales for Customer")
	(println "5. Total Count for Product")
	(println "6. Exit")
	(println " ")
	(println "Enter Choice:")
)

(defn main[]

	(displayMenu)
	
	(let [input (str (read-line))]
		(cond
			(= "1" input) (do (printdata customerdata 1)(recur))
					
			(= "2" input) (do (printdata productdata 2)(recur))
					
  	        (= "3" input) (do (printdata salesdata 3)(recur))
  	        
  	        (= "4" input) (do (totalSales salesdata)(recur))
  	        			
  	        (= "5" input) (do (productCount)(recur))

  	        (= "6" input) (goodbye)

  	        :else (do (println "Enter Correct Choice.")(recur))
		)
	)
)

(main)	
