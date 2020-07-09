# --------------------------------------------------------------------
# Developed with Love for Smartlink Network Systems by :-            | 
#   BITS PILANI Interns(2018)                                        |
#      Dev Arora                                                     |
#      Nikhil Khandelwal                                             |
#      Sasmit Mati                                                   |
#      Shubham Mittal                                                |
#      Shubham Raj                                                   |
# --------------------------------------------------------------------

from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt

from .models import Product,Subgroup2,Subgroup1,Distributor,DistributorFeedback
from django.contrib.auth import authenticate, login
from django.shortcuts import render, redirect
from django.contrib import auth
from django.contrib.auth.models import User
from django.http import Http404,JsonResponse, HttpResponseRedirect,HttpResponse
from django.shortcuts import render, get_object_or_404
import re
import urllib.request, json

def red(request):
    return HttpResponseRedirect('/admin/')

@csrf_exempt
def subgroup3list(request,string):
    if string == "active":
        ap = 'ACTIVE'
    elif string == 'passive':
        ap = 'PASSIVE'
    else:
        ap = 'NONE'
        return HttpResponseRedirect('/not_found/')
    data = []
    for sub1 in Subgroup1.objects.filter(active_passive=ap):
        lis = []
        for sub2 in sub1.subgroup2_set.all():
            lis.append(sub2.name)
        dat = {"subgroup1": sub1.name, "subgroup2": lis}
        data.append(dat)
    return HttpResponse(json.dumps(data), content_type='application/json')

@csrf_exempt
def subgroup2list(request, string):
    data = []
    str1 = string.replace("_-", " ").replace("and", "&").replace("_", "/")
    for prod in Product.objects.filter(subgroup2=Subgroup2.objects.get(name=str1)):
        data.append(prod.model_no)
    model_dic = {"model_list":data}
    return HttpResponse(json.dumps(model_dic), content_type='application/json')

@csrf_exempt
def disp_product(request, string):
    str1 = string.replace("_-", " ").replace("and", "&").replace("_", "/")
    pr = Product.objects.get(model_no__contains=str1)
    data = {"model_no": pr.model_no, "model_name": pr.model_name, "description": pr.description,
            "image": pr.image.url, "pdf": pr.pdf1.url}
    return HttpResponse(json.dumps(data), content_type='application/json')


@csrf_exempt
def splogin(request):
    if request.method == 'POST':
        data = request.POST
        username = data['username']
        password = data['password']
        if User.objects.filter(username=username):
            user = auth.authenticate(username=username, password=password)
            if user is not None:
                login(request, user)
                return JsonResponse({"success": "1"})
        return JsonResponse({"success": "0"})


@csrf_exempt
def logout(request):
    logout(request)
    return JsonResponse({"success": "1"})


@csrf_exempt
def distributor_feedback(request):
    if request.method == 'POST':
        data = request.POST
        distributor = Distributor.objects.get(name=data['name'])
        df = DistributorFeedback()
        df.distributor_name = distributor
        df.feedback = data['feedback']
        df.save()
        return JsonResponse({'success': '1'})
    else:
        return JsonResponse({'success': '0'})


@csrf_exempt
def distributors_list(request,pincode):
    data = []
    for dist in Distributor.objects.filter(pincode=pincode):
        data.append(dist.name)
    dist_dic = {'distributors_list': data}
    return HttpResponse(json.dumps(dist_dic), content_type='application/json')



@csrf_exempt
def new_distributor(request):
    if request.method == 'POST':
        data = request.POST
        print(data)
        with urllib.request.urlopen("http://postalpincode.in/api/pincode/"+data.get('pincode')) as url:
            dat = json.loads(url.read().decode())
        print(dat)
        dist = Distributor()
        dist.name = data.get('name')
        dist.pincode = data.get('pincode')
        dist.address = data.get('address')
        #dist.city = data['city']
        dist.district = dat['PostOffice'][0]['District']
        dist.state = dat['PostOffice'][0]['State']
        dist.save()
        return JsonResponse({"success": '1'})
    else:
        return JsonResponse({"success": '0'})