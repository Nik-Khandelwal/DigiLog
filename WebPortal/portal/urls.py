# --------------------------------------------------------------------
# Developed with Love for Smartlink Network Systems by :-            | 
#   BITS PILANI Interns(2018)                                        |
#      Dev Arora                                                     |
#      Nikhil Khandelwal                                             |
#      Sasmit Mati                                                   |
#      Shubham Mittal                                                |
#      Shubham Raj                                                   |
# --------------------------------------------------------------------


from django.conf.urls import url, include
from .import views

app_name = 'portal'

urlpatterns = [

	url(r'^$', views.red, name="red"),
    url(r'^ap/(?P<string>[\w\-]+)/$', views.subgroup3list, name="subgroup3list"),

    url(r'^sg2/(?P<string>[\w\-]+)/$', views.subgroup2list, name="subgroup1list"),

    url(r'^prod/(?P<string>[\w\-]+)/$', views.disp_product, name="disp_product"),

    url(r'^splogin/$', views.splogin, name="login"),

   	#url(r'^pincode_fetch/(?P<string>[\w\-]+)/$', views.pincode_fetch, name="pincode_fetch"),

   	url(r'^new_distributor/$', views.new_distributor, name="new_distributor"),

   	#url(r'^new_dist/$', views.new_dist, name="new_dist"),

    url(r'^distributors_list/(?P<pincode>[\w\-]+)/$', views.distributors_list, name="distributors_list"),

    url(r'^distributor_feedback/$', views.distributor_feedback, name="distributor_feedback"),
    
]