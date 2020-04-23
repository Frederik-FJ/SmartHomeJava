#!/usr/bin/env python3

from itertools import count
import argparse

from fritzconnection import FritzConnection
from fritzconnection.core.exceptions import FritzServiceError


def get_wlan_status(fc, service, action):
    results = []
    used_service = []
    for n in count(1):
        service_use = service + str(n)
        try:
            result = fc.call_action(service_use, action)
        except FritzServiceError as e:
            break
        results.append(result)
        used_service.append(service_use)
    return results, used_service


def wlan_status_chose(fc, service, action, keys=['NewSSID', 'NewStatus']):
    results, services = get_wlan_status(fc, service, action)
    good_results = [list(range(len(services))), list(range(len(services)))]
    i = 0
    for result in results:
        ii = 0
        for key in keys:

            if (ii == 0):
                good_results[0][i] = result[key]
            else:
                good_results[1][i] = result[key]
            ii += 1
        i += 1;
    return good_results


def get_options():
    parser = argparse.ArgumentParser(description='Get Infos from the FritzBox')
    parser.add_argument('-i', '--ip', help="IP Adresse der FritzBox", required=True)
    parser.add_argument("-p", "--password", help="Passwort für die FritzBox", required=True)
    parser.add_argument('-s', '--service', help="Audzuführender Service", required=True)
    parser.add_argument('-a', '--action', help='Audzuführende Aktion', required=True)
    parser.add_argument('-k', '--keys', help='Keys durch Komma getrennt')

    arguments = parser.parse_args()
    return arguments


if __name__ == '__main__':

    args = get_options();
    key_list = args.keys.split(',')

    fc = FritzConnection(address=args.ip, password=args.password)

    # zum nachschauen von Kategorien aktivieren

    #    result, service = get_wlan_status(fc)
    #    for i, val in enumerate(result):
    #        print(str(service.__getitem__(i)) + ": " + str(result.__getitem__(i)))

    #    print("\n\n\n")

    results, service = get_wlan_status(fc, args.service, args.action)
    for i, result in enumerate(results):
        for key in key_list:
            print(result[key], end=':')
        print("")

