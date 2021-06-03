package dev.usadamasa

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.cloudresourcemanager.v3.CloudResourceManager
import com.google.api.services.cloudresourcemanager.v3.CloudResourceManager.Projects.GetIamPolicy
import com.google.api.services.cloudresourcemanager.v3.model.Binding
import com.google.api.services.cloudresourcemanager.v3.model.GetIamPolicyRequest
import com.google.api.services.cloudresourcemanager.v3.model.Policy
import java.util.*


class App {}

private fun createCloudResourceManagerService(): CloudResourceManager {
    val httpTransport: HttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val jsonFactory: JsonFactory = JacksonFactory.getDefaultInstance()
    val credential = GoogleCredential.getApplicationDefault()
    return CloudResourceManager.Builder(httpTransport, jsonFactory, credential).build()
}

private fun fetchBindings(service: CloudResourceManager, resource: String): List<Binding> {
    val requestBody = GetIamPolicyRequest()
    val request: GetIamPolicy = service.projects().getIamPolicy(resource, requestBody)
    val response: Policy = request.execute()
    return Collections.unmodifiableList(response.bindings)
}

fun main(args: Array<String>) {
    val project = args[0]
    val service: CloudResourceManager = createCloudResourceManagerService()
    val resource = "projects/%s".format(project)
    val bindings = fetchBindings(service, resource)
    bindings.forEach { b ->
        b.members.forEach { m ->
            val elem = m.split(":") + b.role
            println(elem.joinToString(","))
        }
    }
}
