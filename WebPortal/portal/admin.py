# --------------------------------------------------------------------
# Developed with Love for Smartlink Network Systems by :-            | 
#   BITS PILANI Interns(2018)                                        |
#      Dev Arora                                                     |
#      Nikhil Khandelwal                                             |
#      Sasmit Mati                                                   |
#      Shubham Mittal                                                |
#      Shubham Raj                                                   |
# --------------------------------------------------------------------

from django.contrib import admin
from .models import Product, Subgroup2, Subgroup1,Distributor,DistributorFeedback
# Register your models here.


class ProductAdmin(admin.ModelAdmin):
    search_fields = ('model_no', 'model_name')
    list_filter = ('subgroup1', 'subgroup2', 'active_passive')


class Subgroup2Admin(admin.ModelAdmin):
    search_fields = ('name', 'subgroup1')
    list_display = ('name', 'subgroup1')
    list_filter = ('subgroup1',)


def get_admin_url(self):
    return "/admin/portal/subgroup2/%d/" %self.id


class Subgroup1Admin(admin.ModelAdmin):
    search_fields = ('name',)



admin.site.register(Product, ProductAdmin)
admin.site.register(Subgroup1, Subgroup1Admin)
admin.site.register(Subgroup2, Subgroup2Admin)
admin.site.register(Distributor)
admin.site.register(DistributorFeedback)
