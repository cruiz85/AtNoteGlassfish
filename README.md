@Note

@Note is a collaborative annotation system developed at UCM under the auspices of the Google’s
2010 Digital Humanities Award program. @Note lets us retrieve digitized works from Google Books
collection and add annotations to enrich the texts with research and learning purposes: critical editions,
reading activities, e-learning tasks, etc. One of the main features of @Note annotation model, which
distinguishes it from similar approaches, is to promote the collaborative creation of annotation schemas
by communities of researchers, teachers and students, and the use of these schemas in the definition of
annotation activities on literary works. It results in a very flexible and adaptive model, able to be used by
many different communities of experts in literature defending different critical literary theories and for
different annotation tasks. To do so, we have adopted a bottom-up methodology: from the needs and
demands of users to the development of a tool for collaborative digital editing and visualization of
literary base of knowledge. Currently we are enriching @Note with a web-services API making it
possible to export both the annotations and the annotation schemes encoded as JSON data structures.

To install this sofware you will need:

GWT 2.5.0 or up.
Glassfidh server to deploy 3.1.2 or up.

This proyect was made in eclipse plataform , to import it you need also:


EclipseLink 2.4.1 or up.

Java 6.0 or up.

activation.jar

asm-3.1.jar

commons-codec-1.6.jar

commons-fileupload-1.2.2.jar

commons-io-2.4.jar

commons-logging-1.1.1.jar

flexjson-2.1.jar

geronimo-jta_1.1_spec-1.1.1.jar

ghost4j-0.5.0.jar

gwt-incubator-20101117-r1766.j

itext-2.1.7.jar

jackson-core-asl-1.1.1.jar

jaxb-api.jar

jdo2-api-2.3-eb.jar

jersey-client-1.1.5.1.jar

jersey-core-1.1.5.1.jar

jersey-json-1.1.5.1.jar

jersey-server-1.1.5.1.jar

jersey-spring-1.1.5.1.jar

jettison-1.1.jar

jna-3.3.0.jar

jsr107cache-1.1.jar

jsr173_api.jar

jsr311-api-1.1.1.jar

log4j-1.2.15.jar

mail.jar

oauth-client-1.1.5.1.jar

oauth-signature-1.1.5.1.jar

resin.jar

xmlgraphics-commons-1.4.jar

mediafire folder with libs: http://www.mediafire.com/?4mvdy62oginx8 08/04/2013

Instalation:


1.Configure BuiltPath seting GWT version an eclipselink.

2.Insert/rewrite other jar libs in \WebContent\WEB-INF\lib\

3.Configure "src\META-INF\persistence.xml" persistance data with your information database

4.Configure Glassfish JDBC : jdbc/_atnotePool,jdbc/_atnotePool__nontx,jdbc/_atnotePool__pm
