import groovy.json.JsonOutput
import groovyx.net.http.RESTClient
import spock.lang.Specification
import groovy.json.JsonSlurper


class RestSpecification extends Specification {

    RESTClient restClient = new RESTClient("http://private-c6a51-apiarydemo8.apiary-mock.com")

    def 'Check for a car with the license plate MOMMY'() {
        given:
        String licensePlate = "MOMMY"

        when:
        def response = restClient.get( path: '/history',
                query: ['q' : licensePlate])
        String data = JsonOutput.toJson(response.data)
        def content = new JsonSlurper().parseText(data)

        then:
        response.status == 200

        and:
        content[0].color == 'red'
        content[0].city == 'Houston'
        content[0].id == 966635
        content[0].price ==24999
    }


    def 'Check Search for Vehicle Specs by Plate'() {
        given:
        String licensePlate = "MOMMY"

        when:
        def response = restClient.get( path: '/specification',
                query: ['q' : licensePlate])
        String data = JsonOutput.toJson(response.data)
        def content = new JsonSlurper().parseText(data)

        then:
        response.status == 200

        and:

        content[0].make == 'Toyota'
        content[0].model == 'Sienna'
        content[0].version == 'XLE'
        content[0].style == 'Minivan'
        content[0].year == 2013
        content[0].odometer == '55000'

    }

    def 'Check Find Similar Vehicles Nearby with 3 Parameters'() {
        given:
        String make = 'Toyota'
        String model = 'Sienna'
        int zipcode = 77008

        when:
        def response = restClient.get( path: '/getpricelist',
                query: ['q' : 'mk='+make+'&mo='+model+'&zc='+zipcode ])
        String data = JsonOutput.toJson(response.data)
        def content = new JsonSlurper().parseText(data)

        then:
        response.status == 200

        and:

        content[0].id == 966635
        content[0].vin == "PI5P4NPMHD1GY34NH7JU1"

    }


}