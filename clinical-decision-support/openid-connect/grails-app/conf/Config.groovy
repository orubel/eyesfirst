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
// configuration for plugin testing - will not be included in the plugin zip

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
	debug  'org.mitre',
	       'org.mitre.openid'
}

grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
grails.serverURL = "http://localhost:8080/${appName}"

security {
	openid {
		// The application name as reported to the OpenID server
		appName = "${appName}"
		// Set the redirect URL. The following is the default
		//redirectURI = "${grails.serverURL}/openid_connect_login"
		// Create a list of issuers that exist by default in the login page.
		defaultIssuers {
			mitre {
				name = "MITRE"
				url = "https://id.mitre.org/connect/"
			}
			google {
				name = "Google"
				url = "https://accounts.google.com/o/oauth2/auth/"
			}
		}
		// How authorities (roles) are mapped to user accounts: can be either
		// 'config' (configured in this file, easy for setup/development but
		// not recommended for production) or 'domain' (use domain classes).
		// A future version will add a way to use custom classes.
		authoritiesMapper = 'config'
		// The default role for all authenticated users, used by both config and
		// domain
		defaultRole = "ROLE_USER"

		// The following configuration settings are only used by the 'config'
		// authority mapper:

		// Assign roles based on issuers:
		issuers = [
			// Give all users authenticated by "https://id.mitre.org/connect/" the "ROLE_MITRE" role
			"https://id.mitre.org/connect/": "ROLE_MITRE"
		]
		users = [
			// Give a specific user the "ROLE_ADMIN" role - note that the format
			// here is the authority string generated by
			// SubjectIssuerGrantedAuthority, "OIDC_" + username + "_" + issuer
			"OIDC_69cf37d6e6e7462c6bef71561f143071_https://id.mitre.org/connect/": "ROLE_ADMIN"
		]

		// The following configuration settings are only used by the 'domain'
		// authority mapper:
		issuerLookup {
			issuerDomainClassName = 'org.mitre.openid.Issuer'
			issuerPropertyName = 'issuer'
		}
		userLookup {
			userDomainClassName = "org.mitre.openid.User"
			subjectPropertyName = "subject"
			authoritiesPropertyName = "authorities"
		}
		authority {
			nameField = "authority"
		}
		// As a hack, force HTTPS on all requests:
		forceHTTPS = true
	}
}
