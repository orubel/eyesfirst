/*
 * Copyright 2013 The MITRE Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eyesfirst

class Efid {
	String id	//eyes first id

	Date dateCreated // rounded to the month

	static constraints = {
		id blank: false, nullable: false //, unique:true the id is already unique
		//issuer(blank:false, nullable:false)
	}

	static belongsTo = [issuer: EfidIssuer]

	static mapping = {
		id generator: 'assigned' //name: 'efid'  //doesn't work due to Grails bug somewhere.
		version false
		autoTimestamp false
	}

	def beforeInsert() {
		//initialize dateCreated to 1st day of this month in local-time.
		def now = Calendar.instance
		def cal = Calendar.instance
		cal.clear()
		TimeZone utc = TimeZone.getTimeZone("UTC");
		now.timeZone = utc;
		cal.timeZone = utc;
		cal.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1)
		dateCreated = cal.time
	}
}
