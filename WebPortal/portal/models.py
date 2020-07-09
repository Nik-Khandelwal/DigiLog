# --------------------------------------------------------------------
# Developed with Love for Smartlink Network Systems by :-            | 
#   BITS PILANI Interns(2018)                                        |
#      Dev Arora                                                     |
#      Nikhil Khandelwal                                             |
#      Sasmit Mati                                                   |
#      Shubham Mittal                                                |
#      Shubham Raj                                                   |
# --------------------------------------------------------------------

from __future__ import unicode_literals
from django.db import models
from django.contrib.auth.models import User,AbstractUser

ACTIVE_PASSIVE = [
        ('ACTIVE', 'ACTIVE'),
        ('PASSIVE', 'PASSIVE'),
    ]


class Subgroup1(models.Model):
    name = models.CharField(max_length=1000)
    active_passive = models.CharField(max_length=50, choices=ACTIVE_PASSIVE, default='ACTIVE')

    def __str__(self):
        return self.name


class Subgroup2(models.Model):
    name = models.CharField(max_length=1000)
    subgroup1 = models.ForeignKey(Subgroup1, on_delete=models.CASCADE)

    def __str__(self):
        return str(self.name+"-"+self.subgroup1.name)


class Product(models.Model):
    model_no = models.CharField(primary_key=True, max_length=1000)
    model_name = models.CharField(max_length=10000)
    active_passive = models.CharField(max_length=50, choices=ACTIVE_PASSIVE, default='ACTIVE')
    subgroup1 = models.ForeignKey(Subgroup1, on_delete=models.CASCADE)
    subgroup2 = models.ForeignKey(Subgroup2, on_delete=models.CASCADE)
    description = models.TextField()
    image = models.FileField(upload_to="images/", null=True, blank=True)
    pdf1 = models.FileField(upload_to="documents/", null=True, blank=True)
    pdf2 = models.FileField(upload_to="documents/", null=True, blank=True)
    website_url = models.CharField(max_length=1000000)

    def __str__(self):
        return str(self.model_name+"-"+self.model_no)


#class Salesperson(AbstractUser):
#    region = models.CharField(max_length=1000)
#    mobile_no = models.CharField(max_)

#    def __str__(self):
#        return str(self.get_full_name())


class Distributor(models.Model):
    name = models.CharField(max_length=10000000)
    address = models.TextField()
    city = models.CharField(max_length=10000)
    district = models.CharField(max_length=10000)
    state = models.CharField(max_length=100000)
    pincode = models.CharField(max_length=1000)

    def __str__(self):
        return str(self.name+"-"+self.district)


class DistributorFeedback(models.Model):
    distributor_name = models.ForeignKey(Distributor, on_delete=models.CASCADE)
    feedback = models.TextField()











